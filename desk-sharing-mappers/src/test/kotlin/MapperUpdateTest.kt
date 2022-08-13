import kotlinx.datetime.LocalDate
import model.*
import org.junit.Test
import ru.otus.otuskotlin.deskSharing.api.v1.models.*
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandStatus
import stubs.DemandStubs
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MapperUpdateTest {

    @Test
    fun fromTransport() {
        val request = DemandUpdateRequest(
            requestId = "2e07327d-47e7-4da1-9c89-eff53a37cdb7",
            debug = DemandDebug(
                mode = DemandRequestDebugMode.STUB,
                stub = DemandRequestDebugStubs.SUCCESS
            ),
            demand = DemandUpdateObjectDto(
                date = "2022-01-01",
                bookingDate = "2022-01-10",
                employeeId = "2e07327d-47e7-4da1-9c89-eff53a37c000",
                branchId = "2e07327d-47e7-4da1-9c89-eff53a37c111",
                buildingId = "2e07327d-47e7-4da1-9c89-eff53a37c222",
                status = DemandStatus.ACCEPTED,
                workDeskId = "2e07327d-47e7-4da1-9c89-eff53a37caaa"
            )
        )

        val context = DemandContext()
        context.fromTransport(request)

        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cdb7", context.requestId.asString())
        assertEquals(DemandStubs.SUCCESS, context.stubCase)
        assertEquals(DemandCommand.UPDATE, context.command)
        assertEquals(DemandState.NONE, context.state)
        assertEquals(DskShrngWorkMode.STUB, context.workMode)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cdb7", context.requestId.asString())
        assertEquals(LocalDate(2022, 1, 1), context.demandRequest.date)
        assertEquals(LocalDate(2022, 1, 10), context.demandRequest.bookingDate)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37c000", context.demandRequest.employeeId.asString())
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37c111", context.demandRequest.branchId.asString())
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37c222", context.demandRequest.buildingId.asString())
        assertEquals(model.DemandStatus.ACCEPTED, context.demandRequest.status)
        assertEquals("", context.demandRequest.number)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37caaa", context.demandRequest.workDeskId.asString())
        assertEquals("", context.demandRequest.declineReason)
        assertEquals(DemandUserId.NONE, context.demandRequest.userId)
    }

    @Test
    fun toTransport() {
        val context = DemandContext(
            requestId = DemandRequestId("2e07327d-47e7-4da1-9c89-eff53a37cdb7"),
            command = DemandCommand.UPDATE,
            state = DemandState.RUNNING,
            workMode = DskShrngWorkMode.STUB,
            stubCase = DemandStubs.SUCCESS,
            demandResponse = DemandDto(
                date = LocalDate(2022, 1, 1),
                bookingDate = LocalDate(2022, 1, 10),
                branchId = DskShrngId("2e07327d-47e7-4da1-9c89-eff53a37c111"),
                buildingId = DskShrngId("2e07327d-47e7-4da1-9c89-eff53a37c222"),
                employeeId = DskShrngId("2e07327d-47e7-4da1-9c89-eff53a37c000"),
                status = model.DemandStatus.ACCEPTED,
                demandId = DskShrngId("2e07327d-47e7-4da1-9c89-eff53a37cfff"),
                workDeskId = DskShrngId("2e07327d-47e7-4da1-9c89-eff53a37caaa"),
                number = "01/001"
            )
        )

        val response = context.toTransportDemand() as DemandUpdateResponse

        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cdb7", response.requestId)
        assertEquals(ResponseResult.SUCCESS, response.result)
        assertEquals("2022-01-01", response.demand?.date)
        assertEquals("2022-01-10", response.demand?.bookingDate)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37c000", response.demand?.employeeId)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37c111", response.demand?.branchId)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37c222", response.demand?.buildingId)
        assertEquals(DemandStatus.ACCEPTED, response.demand?.status)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cfff", response.demand?.demandId)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37caaa", response.demand?.workDeskId)
        assertEquals("01/001", response.demand?.number)
        assertNull(response.demand?.declineReason)
        assertNull(response.demand?.userId)
        assertNull(response.demand?.lock)
    }
}