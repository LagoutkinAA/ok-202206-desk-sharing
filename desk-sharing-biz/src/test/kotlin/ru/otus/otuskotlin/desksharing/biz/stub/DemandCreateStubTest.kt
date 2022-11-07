package ru.otus.otuskotlin.desksharing.biz.stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import ru.otus.otuskotlin.desksharing.biz.DemandProcessor
import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.NONE
import ru.otus.otuskotlin.desksharing.common.model.DemandCommand
import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DemandRequestId
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.model.DemandStatus
import ru.otus.otuskotlin.desksharing.common.model.DemandUserId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngWorkMode
import ru.otus.otuskotlin.desksharing.common.model.WorkDeskNumber
import ru.otus.otuskotlin.desksharing.common.now
import ru.otus.otuskotlin.desksharing.common.stubs.DemandStubs
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class DemandCreateStubTest {

    private val processor = DemandProcessor()
    val demandId = DskShrngId("2e07327d-ffff-4da1-9c89-eff53a37c000")
    val employeeId = DskShrngId("e777")
    val userId = DemandUserId("u777")
    val date = LocalDate.now()
    val bookingDate = LocalDate.now().plus(DatePeriod(days = 1))
    val workDeskNumber = WorkDeskNumber("1/1")
    val number = "1"

    @Test
    fun createSuccess() = runTest {

        val ctx = DemandContext(
            requestId = DemandRequestId("2e07327d-47e7-4da1-9c89-eff53a37cdb7"),
            command = DemandCommand.CREATE,
            state = DemandState.NONE,
            workMode = DskShrngWorkMode.STUB,
            stubCase = DemandStubs.SUCCESS,
            demandRequest = DemandDto(
                date = LocalDate.NONE,
                bookingDate = bookingDate,
                employeeId = employeeId,
                status = DemandStatus.NONE,
                number = "",
                userId = userId
            )
        )
        processor.exec(ctx)
        assertEquals(demandId, ctx.demandResponse.demandId)
        assertEquals(DemandStatus.ACCEPTED, ctx.demandResponse.status)
        assertEquals(date, ctx.demandResponse.date)
        assertEquals(number, ctx.demandResponse.number)
        assertEquals(workDeskNumber, ctx.demandResponse.workDeskNumber)
    }

    @Test
    fun createDeclined() = runTest {

        val ctx = DemandContext(
            requestId = DemandRequestId("2e07327d-47e7-4da1-9c89-eff53a37cdb7"),
            command = DemandCommand.CREATE,
            state = DemandState.NONE,
            workMode = DskShrngWorkMode.STUB,
            stubCase = DemandStubs.NO_FREE_WORK_DESK,
            demandRequest = DemandDto(
                date = LocalDate.NONE,
                bookingDate = bookingDate,
                employeeId = employeeId,
                status = DemandStatus.NONE,
                number = "",
                userId = userId
            )
        )
        processor.exec(ctx)
        assertEquals(demandId, ctx.demandResponse.demandId)
        assertEquals(DemandStatus.DECLINED, ctx.demandResponse.status)
        assertEquals(date, ctx.demandResponse.date)
        assertEquals(number, ctx.demandResponse.number)
        assertEquals(WorkDeskNumber.NONE, ctx.demandResponse.workDeskNumber)
        assertEquals("No free workdesk", ctx.demandResponse.declineReason)
    }

    @Test
    fun badId() = runTest {
        val ctx = DemandContext(
            requestId = DemandRequestId("2e07327d-47e7-4da1-9c89-eff53a37cdb7"),
            command = DemandCommand.CREATE,
            state = DemandState.NONE,
            workMode = DskShrngWorkMode.STUB,
            stubCase = DemandStubs.BAD_ID,
            demandRequest = DemandDto(
                date = date,
                bookingDate = bookingDate,
                employeeId = employeeId,
                status = DemandStatus.NONE,
                number = number,
                userId = userId
            )
        )
        processor.exec(ctx)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun validationError() = runTest {
        val ctx = DemandContext(
            requestId = DemandRequestId("2e07327d-47e7-4da1-9c89-eff53a37cdb7"),
            command = DemandCommand.CREATE,
            state = DemandState.NONE,
            workMode = DskShrngWorkMode.STUB,
            stubCase = DemandStubs.VALIDATION_ERROR,
            demandRequest = DemandDto(
                date = date,
                bookingDate = bookingDate,
                employeeId = employeeId,
                status = DemandStatus.NONE,
                number = number,
                userId = userId
            )
        )
        processor.exec(ctx)
        assertEquals("bookingDate", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = DemandContext(
            requestId = DemandRequestId("2e07327d-47e7-4da1-9c89-eff53a37cdb7"),
            command = DemandCommand.CREATE,
            state = DemandState.NONE,
            workMode = DskShrngWorkMode.STUB,
            stubCase = DemandStubs.DB_ERROR,
            demandRequest = DemandDto(
                date = date,
                bookingDate = bookingDate,
                employeeId = employeeId,
                status = DemandStatus.NONE,
                number = number,
                userId = userId
            )
        )
        processor.exec(ctx)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }
}
