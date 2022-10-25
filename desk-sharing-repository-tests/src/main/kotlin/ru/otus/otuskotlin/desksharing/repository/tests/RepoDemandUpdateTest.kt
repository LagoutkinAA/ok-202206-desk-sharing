package ru.otus.otuskotlin.desksharing.repository.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandRequest
import ru.otus.otuskotlin.desksharing.common.repository.IDemandRepository
import ru.otus.otuskotlin.marketplace.backend.repo.tests.runRepoTest
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoDemandUpdateTest {
    abstract val repo: IDemandRepository
    protected val updateSucc = initObjects[0]
    protected val updateConc = initObjects[1]
    protected val updateIdNotFound = DskShrngId("ad-repo-update-not-found")
    protected val lockBad = "20000000-0000-0000-0000-000000000009"
    protected val lockNew = "20000000-0000-0000-0000-000000000002"

    private val reqUpdateSuccess = DemandDto(
        demandId = updateSucc.demandId,
        employeeId = DskShrngId("owner-123"),
        lock = initObjects.first().lock,
    )
    private val reqUpdateNotFound = DemandDto(
        demandId = updateIdNotFound,
        employeeId = DskShrngId("owner-123"),
        lock = initObjects.first().lock,
    )
    private val reqUpdateConc = DemandDto(
        demandId = updateConc.demandId,
        employeeId = DskShrngId("owner-123"),
        lock = lockBad,
    )

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateDemand(DbDemandRequest(reqUpdateSuccess))
        assertEquals(true, result.isSuccess)
        assertEquals(lockNew, result.data?.lock)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateDemand(DbDemandRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateDemand(DbDemandRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitDemands("update") {
        override val initObjects: List<DemandDto> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
