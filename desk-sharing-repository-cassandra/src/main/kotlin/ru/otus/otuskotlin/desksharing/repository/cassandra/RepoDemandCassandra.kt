package ru.otus.otuskotlin.desksharing.repository.cassandra

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.future.await
import kotlinx.coroutines.withTimeout
import org.slf4j.LoggerFactory
import ru.otus.otuskotlin.desksharing.common.helpers.asDemandError
import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DemandError
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandFilterRequest
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandIdRequest
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandRequest
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandResponse
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandsResponse
import ru.otus.otuskotlin.desksharing.common.repository.IDemandRepository
import ru.otus.otuskotlin.desksharing.repository.cassandra.model.DemandCassandraEntity
import java.util.concurrent.CompletionStage

class RepoDemandCassandra(
    private val dao: DemandCassandraDAO,
    private val timeoutMillis: Long = 300_000,
    private val randomUuid: () -> String = { uuid4().toString() }
) : IDemandRepository {
    private val log = LoggerFactory.getLogger(javaClass)

    private fun errorToDemandResponse(e: Exception) = DbDemandResponse.error(e.asDemandError())
    private fun errorToDemandsResponse(e: Exception) = DbDemandsResponse.error(e.asDemandError())

    private suspend inline fun <DbRes, Response> doDbAction(
        name: String,
        crossinline daoAction: () -> CompletionStage<DbRes>,
        okToResponse: (DbRes) -> Response,
        errorToResponse: (Exception) -> Response
    ): Response = doDbAction(
        name,
        {
            val dbRes = withTimeout(timeoutMillis) { daoAction().await() }
            okToResponse(dbRes)
        },
        errorToResponse
    )

    private suspend inline fun readAndDoDbAction(
        name: String,
        demandId: DskShrngId,
        successResult: DemandDto?,
        daoAction: () -> CompletionStage<Boolean>,
        errorToResponse: (Exception) -> DbDemandResponse
    ): DbDemandResponse =
        if (demandId == DskShrngId.NONE)
            ID_IS_EMPTY
        else doDbAction(
            name,
            {
                val read = dao.read(demandId.asString()).await()
                if (read == null) ID_NOT_FOUND
                else {
                    val success = daoAction().await()
                    if (success) DbDemandResponse.success(successResult ?: read.toInternal())
                    else DbDemandResponse(
                        read.toInternal(),
                        false,
                        CONCURRENT_MODIFICATION.errors
                    )
                }
            },
            errorToResponse
        )

    private inline fun <Response> doDbAction(
        name: String,
        daoAction: () -> Response,
        errorToResponse: (Exception) -> Response
    ): Response =
        try {
            daoAction()
        } catch (e: Exception) {
            log.error("Failed to $name", e)
            errorToResponse(e)
        }

    override suspend fun createDemand(rq: DbDemandRequest): DbDemandResponse {
        val new = rq.demand.copy(demandId = DskShrngId(randomUuid()), lock = randomUuid())
        return doDbAction(
            "create",
            { dao.create(DemandCassandraEntity(new)) },
            { DbDemandResponse.success(new) },
            ::errorToDemandResponse
        )
    }

    override suspend fun readDemand(rq: DbDemandIdRequest): DbDemandResponse =
        if (rq.id == DskShrngId.NONE)
            ID_IS_EMPTY
        else doDbAction(
            "read",
            { dao.read(rq.id.asString()) },
            { found ->
                if (found != null) DbDemandResponse.success(found.toInternal())
                else ID_NOT_FOUND
            },
            ::errorToDemandResponse
        )

    override suspend fun updateDemand(rq: DbDemandRequest): DbDemandResponse {
        val prevLock = rq.demand.lock
        val new = rq.demand.copy(lock = randomUuid())
        val dto = DemandCassandraEntity(new)

        return readAndDoDbAction(
            "update",
            rq.demand.demandId,
            new,
            { dao.update(dto, prevLock) },
            ::errorToDemandResponse
        )
    }

    override suspend fun deleteDemand(rq: DbDemandIdRequest): DbDemandResponse =
        readAndDoDbAction(
            "delete",
            rq.id,
            null,
            { dao.delete(rq.id.asString(), rq.lock) },
            ::errorToDemandResponse
        )


    override suspend fun searchDemand(rq: DbDemandFilterRequest): DbDemandsResponse =
        doDbAction(
            "search",
            { dao.search(rq) },
            { found ->
                DbDemandsResponse.success(found.map { it.toInternal() })
            },
            ::errorToDemandsResponse
        )

    companion object {
        private val ID_IS_EMPTY = DbDemandResponse.error(DemandError(field = "demandId", message = "Id is empty"))
        private val ID_NOT_FOUND =
            DbDemandResponse.error(DemandError(field = "demandId", code = "not-found", message = "Not Found"))
        private val CONCURRENT_MODIFICATION =
            DbDemandResponse.error(
                DemandError(
                    field = "lock",
                    code = "concurrency",
                    message = "Concurrent modification"
                )
            )
    }
}