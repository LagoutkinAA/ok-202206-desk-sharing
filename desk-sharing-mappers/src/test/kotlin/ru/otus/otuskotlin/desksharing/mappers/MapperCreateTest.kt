package ru.otus.otuskotlin.desksharing.mappers

import fromTransport
import kotlinx.datetime.LocalDate
import org.junit.Test
import ru.otus.otuskotlin.deskSharing.api.v1.models.*
import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.*
import ru.otus.otuskotlin.desksharing.common.stubs.DemandStubs
import toTransportDemand
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MapperCreateTest {

    @Test
    fun fromTransport() {
        val request = DemandCreateRequest(
            requestId = "2e07327d-47e7-4da1-9c89-eff53a37cdb7",
            debug = DemandDebug(
                mode = DemandRequestDebugMode.STUB,
                stub = DemandRequestDebugStubs.SUCCESS
            ),
            demand = DemandCreateObjectDto(
                date = "2022-01-01",
                bookingDate = "2022-01-10",
                employeeId = "2e07327d-47e7-4da1-9c89-eff53a37c000",
                status = DemandApiStatus.NEW
            )
        )

        val context = DemandContext()
        context.fromTransport(request)

        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cdb7", context.requestId.asString())
        assertEquals(DemandStubs.SUCCESS, context.stubCase)
        assertEquals(DemandCommand.CREATE, context.command)
        assertEquals(DemandState.NONE, context.state)
        assertEquals(DskShrngWorkMode.STUB, context.workMode)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cdb7", context.requestId.asString())
        assertEquals(LocalDate(2022, 1, 1), context.demandRequest.date)
        assertEquals(LocalDate(2022, 1, 10), context.demandRequest.bookingDate)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37c000", context.demandRequest.employeeId.asString())
        assertEquals(ru.otus.otuskotlin.desksharing.common.model.DemandStatus.NEW, context.demandRequest.status)
        assertEquals("", context.demandRequest.number)
        assertEquals(WorkDeskNumber.NONE, context.demandRequest.workDeskNumber)
        assertEquals("", context.demandRequest.declineReason)
        assertEquals(DemandUserId.NONE, context.demandRequest.userId)
    }

    @Test
    fun toTransport() {
        val context = DemandContext(
            requestId = DemandRequestId("2e07327d-47e7-4da1-9c89-eff53a37cdb7"),
            command = DemandCommand.CREATE,
            state = DemandState.RUNNING,
            workMode = DskShrngWorkMode.STUB,
            stubCase = DemandStubs.SUCCESS,
            demandResponse = DemandDto(
                date = LocalDate(2022, 1, 1),
                bookingDate = LocalDate(2022, 1, 10),
                employeeId = DskShrngId("2e07327d-47e7-4da1-9c89-eff53a37c000"),
                status = ru.otus.otuskotlin.desksharing.common.model.DemandStatus.ERROR,
                demandId = DskShrngId("2e07327d-47e7-4da1-9c89-eff53a37cfff")
            ),
            errors = mutableListOf(
                DemandError(
                    code = "err",
                    group = "request",
                    field = "employeeId",
                    message = "wrong employeeId",
                )
            )
        )

        val response = context.toTransportDemand() as DemandCreateResponse

        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cdb7", response.requestId)
        assertEquals(ResponseResult.SUCCESS, response.result)
        assertEquals("2022-01-01", response.demand?.date)
        assertEquals("2022-01-10", response.demand?.bookingDate)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37c000", response.demand?.employeeId)
        assertEquals(DemandApiStatus.ERROR, response.demand?.status)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cfff", response.demand?.demandId)
        assertNull(response.demand?.workDeskNumber)
        assertNull(response.demand?.number)
        assertNull(response.demand?.declineReason)
        assertNull(response.demand?.userId)
        assertNull(response.demand?.lock)
        assertEquals(1, response.errors?.size)
        assertEquals("err", response.errors?.firstOrNull()?.code)
        assertEquals("request", response.errors?.firstOrNull()?.group)
        assertEquals("employeeId", response.errors?.firstOrNull()?.field)
        assertEquals("wrong employeeId", response.errors?.firstOrNull()?.message)
    }
}