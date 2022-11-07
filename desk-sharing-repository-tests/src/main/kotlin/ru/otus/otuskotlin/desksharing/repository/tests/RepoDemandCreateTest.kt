package ru.otus.otuskotlin.desksharing.repository.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import org.junit.Test
import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DemandStatus
import ru.otus.otuskotlin.desksharing.common.model.DemandUserId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.model.WorkDeskNumber
import ru.otus.otuskotlin.desksharing.common.now
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandRequest
import ru.otus.otuskotlin.desksharing.common.repository.IDemandRepository
import ru.otus.otuskotlin.marketplace.backend.repo.tests.runRepoTest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoDemandCreateTest {
    abstract val repo: IDemandRepository

    protected open val lockNew: String = "20000000-0000-0000-0000-000000000002"

    protected val createObj = DemandDto(
        date = LocalDate.now(),
        bookingDate = LocalDate.now().plus(1, DateTimeUnit.DAY),
        employeeId = DskShrngId("e777"),
        status = DemandStatus.ACCEPTED,
        demandId = DskShrngId("create-001"),
        workDeskNumber = WorkDeskNumber("1"),
        number = "1",
        userId = DemandUserId("e777"),
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createDemand(DbDemandRequest(createObj))
        val expected = createObj.copy(demandId = result.data?.demandId ?: DskShrngId.NONE)
        assertEquals(true, result.isSuccess)
        assertNotEquals(DskShrngId.NONE, result.data?.demandId)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
        assertEquals(expected.number, result.data?.number)
        assertEquals(expected.date, result.data?.date)
        assertEquals(expected.bookingDate, result.data?.bookingDate)
        assertEquals(expected.employeeId, result.data?.employeeId)
        assertEquals(expected.status, result.data?.status)
        assertEquals(expected.demandId, result.data?.demandId)
        assertEquals(expected.workDeskNumber, result.data?.workDeskNumber)
        assertEquals(expected.userId, result.data?.userId)

    }

    companion object : BaseInitDemands("create") {
        override val initObjects: List<DemandDto> = emptyList()
    }
}
