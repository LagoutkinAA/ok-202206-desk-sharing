package ru.otus.otuskotlin.desksharing.repository.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandIdRequest
import ru.otus.otuskotlin.desksharing.common.repository.IDemandRepository
import ru.otus.otuskotlin.marketplace.backend.repo.tests.runRepoTest
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoDemandDeleteTest {
    abstract val repo: IDemandRepository

    @Test
    fun deleteSuccess() = runRepoTest {
        val result = repo.deleteDemand(DbDemandIdRequest(successId, lock = lockOld))

        assertEquals(true, result.isSuccess)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockOld, result.data?.lock)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readDemand(DbDemandIdRequest(notFoundId, lock = lockOld))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("demandId", error?.field)
    }

    @Test
    fun deleteConcurrency() = runRepoTest {
        val result = repo.deleteDemand(DbDemandIdRequest(concurrencyId, lock = lockBad))

        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(lockOld, result.data?.lock)
    }

    companion object : BaseInitDemands("delete") {
        override val initObjects: List<DemandDto> = listOf(
            createInitTestModel("delete"),
            createInitTestModel("deleteLock"),
        )
        val successId = DskShrngId(initObjects[0].demandId.asString())
        val notFoundId = DskShrngId("ad-repo-delete-notFound")
        val concurrencyId = DskShrngId(initObjects[1].demandId.asString())
    }
}
