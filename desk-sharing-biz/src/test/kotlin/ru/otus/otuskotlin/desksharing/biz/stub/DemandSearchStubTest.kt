package ru.otus.otuskotlin.desksharing.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import now
import ru.otus.otuskotlin.desksharing.biz.DemandProcessor
import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandCommand
import ru.otus.otuskotlin.desksharing.common.model.DemandFilter
import ru.otus.otuskotlin.desksharing.common.model.DemandRequestId
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.model.DemandUserId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngWorkMode
import ru.otus.otuskotlin.desksharing.common.model.WorkDeskNumber
import ru.otus.otuskotlin.desksharing.common.stubs.DemandStubs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class DemandSearchStubTest {

    private val processor = DemandProcessor()
    val demandId = DskShrngId("d777")
    val employeeId = DskShrngId("e777")
    val userId = DemandUserId("u777")
    val date = LocalDate.now()
    val bookingDate = LocalDate.now().plus(1, DateTimeUnit.DAY)
    val workDeskNumber = WorkDeskNumber("1/1")
    val number = "1"
    val filter = DemandFilter(employeeId = employeeId, dateFrom = LocalDate.now(), dateTo = LocalDate.now())

    @Test
    fun search() = runTest {

        val ctx = DemandContext(
            requestId = DemandRequestId("2e07327d-47e7-4da1-9c89-eff53a37cdb7"),
            command = DemandCommand.SEARCH,
            state = DemandState.NONE,
            workMode = DskShrngWorkMode.STUB,
            stubCase = DemandStubs.SUCCESS,
            demandFilterRequest = filter
        )
        processor.exec(ctx)
        assertTrue(ctx.demandsResponse.size > 1)
        val first = ctx.demandsResponse.firstOrNull() ?: fail("Empty response list")
        assertEquals(employeeId, first.employeeId)
    }


    @Test
    fun badId() = runTest {
        val ctx = DemandContext(
            requestId = DemandRequestId("2e07327d-47e7-4da1-9c89-eff53a37cdb7"),
            command = DemandCommand.SEARCH,
            state = DemandState.NONE,
            workMode = DskShrngWorkMode.STUB,
            stubCase = DemandStubs.BAD_ID,
            demandFilterRequest = filter
        )
        processor.exec(ctx)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun validationError() = runTest {
        val ctx = DemandContext(
            requestId = DemandRequestId("2e07327d-47e7-4da1-9c89-eff53a37cdb7"),
            command = DemandCommand.SEARCH,
            state = DemandState.NONE,
            workMode = DskShrngWorkMode.STUB,
            stubCase = DemandStubs.VALIDATION_ERROR,
            demandFilterRequest = filter
        )
        processor.exec(ctx)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = DemandContext(
            requestId = DemandRequestId("2e07327d-47e7-4da1-9c89-eff53a37cdb7"),
            command = DemandCommand.SEARCH,
            state = DemandState.NONE,
            workMode = DskShrngWorkMode.STUB,
            stubCase = DemandStubs.DB_ERROR,
            demandFilterRequest = filter
        )
        processor.exec(ctx)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }
}
