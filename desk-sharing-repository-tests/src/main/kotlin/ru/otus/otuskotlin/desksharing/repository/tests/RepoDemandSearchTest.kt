package ru.otus.otuskotlin.desksharing.repository.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.datetime.LocalDate
import now
import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandFilterRequest
import ru.otus.otuskotlin.desksharing.common.repository.IDemandRepository
import ru.otus.otuskotlin.marketplace.backend.repo.tests.runRepoTest
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoDemandSearchTest {
    abstract val repo: IDemandRepository

    @Test
    fun searchEmployee() = runRepoTest {
        val result = repo.searchDemand(DbDemandFilterRequest(employeeId = searchEmployeeId))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initObjects[1], initObjects[3])
        assertEquals(expected, result.data?.sortedBy { it.demandId.asString() })
        assertEquals(emptyList(), result.errors)
    }

    companion object : BaseInitDemands("search") {

        val searchEmployeeId = DskShrngId("owner-124")
        override val initObjects: List<DemandDto> = listOf(
            createInitTestModel("ad1"),
            createInitTestModel("ad2", employeeId = searchEmployeeId),
            createInitTestModel("ad3", bookingDate = LocalDate.now()),
            createInitTestModel("ad4", employeeId = searchEmployeeId)
        )
    }
}
