package ru.otus.otuskotlin.desksharing.repository.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.LocalDate
import ru.otus.otuskotlin.desksharing.common.MAX
import ru.otus.otuskotlin.desksharing.common.NONE
import ru.otus.otuskotlin.desksharing.common.helpers.errorRepoConcurrency
import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DemandError
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandFilterRequest
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandIdRequest
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandRequest
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandResponse
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandsResponse
import ru.otus.otuskotlin.desksharing.common.repository.IDemandRepository
import ru.otus.otuskotlin.desksharing.repository.inmemory.model.DemandEntity
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class DemandRepoInMemory(
    initObjects: List<DemandDto> = emptyList(),
    ttl: Duration = 2.minutes,
    val randomUuid: () -> String = { uuid4().toString() },
) : IDemandRepository {
    /**
     * Инициализация кеша с установкой "времени жизни" данных после записи
     */
    private val cache = Cache.Builder()
        .expireAfterWrite(ttl)
        .build<String, DemandEntity>()
    private val mutex: Mutex = Mutex()

    init {
        initObjects.forEach {
            save(it)
        }
    }

    private fun save(ad: DemandDto) {
        val entity = DemandEntity(ad)
        if (entity.demandId == null) {
            return
        }
        cache.put(entity.demandId, entity)
    }

    override suspend fun createDemand(rq: DbDemandRequest): DbDemandResponse {
        val key = randomUuid()
        val ad = rq.demand.copy(demandId = DskShrngId(key), lock = randomUuid())
        val entity = DemandEntity(ad)
        cache.put(key, entity)
        return DbDemandResponse(
            data = ad,
            isSuccess = true,
        )
    }

    override suspend fun readDemand(rq: DbDemandIdRequest): DbDemandResponse {
        val key = rq.id.takeIf { it != DskShrngId.NONE }?.asString() ?: return resultErrorEmptyId
        return cache.get(key)
            ?.let {
                DbDemandResponse(
                    data = it.toInternal(),
                    isSuccess = true,
                )
            } ?: resultErrorNotFound
    }

    override suspend fun updateDemand(rq: DbDemandRequest): DbDemandResponse {
        val key = rq.demand.demandId.takeIf { it != DskShrngId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.demand.lock ?: return resultErrorEmptyLock
        val newDemand = rq.demand.copy(lock = randomUuid())
        val entity = DemandEntity(newDemand)
        return mutex.withLock {
            val oldDemand = cache.get(key)
            when {
                oldDemand == null -> resultErrorNotFound
                oldDemand.lock != oldLock -> DbDemandResponse(
                    data = oldDemand.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(oldLock, oldDemand.lock))
                )

                else -> {
                    cache.put(key, entity)
                    DbDemandResponse(
                        data = newDemand,
                        isSuccess = true,
                    )
                }
            }
        }
    }

    override suspend fun deleteDemand(rq: DbDemandIdRequest): DbDemandResponse {
        val key = rq.id.takeIf { it != DskShrngId.NONE }?.asString() ?: return resultErrorEmptyId
        val oldLock = rq.lock.takeIf { it.isNotBlank() } ?: return resultErrorEmptyLock
        return mutex.withLock {
            val oldDemand = cache.get(key)
            when {
                oldDemand == null -> resultErrorNotFound
                oldDemand.lock != oldLock -> DbDemandResponse(
                    data = oldDemand.toInternal(),
                    isSuccess = false,
                    errors = listOf(errorRepoConcurrency(oldLock, oldDemand.lock))
                )

                else -> {
                    cache.invalidate(key)
                    DbDemandResponse(
                        data = oldDemand.toInternal(),
                        isSuccess = true,
                    )
                }
            }
        }
    }

    /**
     * Поиск объявлений по фильтру
     * Если в фильтре не установлен какой-либо из параметров - по нему фильтрация не идет
     */
    override suspend fun searchDemand(rq: DbDemandFilterRequest): DbDemandsResponse {
        val result = cache.asMap().asSequence()
            .filter { entry ->
                rq.employeeId.takeIf { it != DskShrngId.NONE }?.let {
                    it.asString() == entry.value.employeeId
                } ?: true
            }
            .filter { entry ->
                rq.dateFrom.takeIf { it != LocalDate.NONE }?.let {
                    (entry.value.bookingDate ?: LocalDate.NONE) >= it
                } ?: true
            }
            .filter { entry ->
                rq.dateTo.takeIf { it != LocalDate.NONE }?.let {
                    (entry.value.bookingDate ?: LocalDate.MAX) <= it
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        return DbDemandsResponse(
            data = result,
            isSuccess = true
        )
    }

    companion object {
        val resultErrorEmptyId = DbDemandResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                DemandError(
                    code = "id-empty",
                    group = "validation",
                    field = "demandId",
                    message = "Id must not be null or blank"
                )
            )
        )
        val resultErrorEmptyLock = DbDemandResponse(
            data = null,
            isSuccess = false,
            errors = listOf(
                DemandError(
                    code = "lock-empty",
                    group = "validation",
                    field = "lock",
                    message = "Lock must not be null or blank"
                )
            )
        )
        val resultErrorNotFound = DbDemandResponse(
            isSuccess = false,
            data = null,
            errors = listOf(
                DemandError(
                    code = "not-found",
                    field = "demandId",
                    message = "Not Found"
                )
            )
        )
    }
}
