import kotlinx.datetime.LocalDate
import model.*
import org.junit.Test
import ru.otus.otuskotlin.deskSharing.api.v1.models.*
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandStatus
import stubs.DemandStubs
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MapperDeleteTest {

    @Test
    fun fromTransport() {
        val request = DemandDeleteRequest(
            requestId = "2e07327d-47e7-4da1-9c89-eff53a37cdb7",
            debug = DemandDebug(
                mode = DemandRequestDebugMode.STUB,
                stub = DemandRequestDebugStubs.SUCCESS
            ),
            demand = DemandDeleteObjectDto(
                demandId = "2e07327d-47e7-4da1-9c89-eff53a37cfff"
            )
        )

        val context = DemandContext()
        context.fromTransport(request)

        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cdb7", context.requestId.asString())
        assertEquals(DemandStubs.SUCCESS, context.stubCase)
        assertEquals(DemandCommand.DELETE, context.command)
        assertEquals(DemandState.NONE, context.state)
        assertEquals(DskShrngWorkMode.STUB, context.workMode)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cfff", context.demandRequest.demandId.asString())
    }

    @Test
    fun toTransport() {
        val context = DemandContext(
            requestId = DemandRequestId("2e07327d-47e7-4da1-9c89-eff53a37cdb7"),
            command = DemandCommand.DELETE,
            state = DemandState.RUNNING,
            workMode = DskShrngWorkMode.STUB,
            stubCase = DemandStubs.SUCCESS,
            demandResponse = DemandDto(
                date = LocalDate(2022, 1, 1),
                bookingDate = LocalDate(2022, 1, 10),
                branchId = DskShrngId("2e07327d-47e7-4da1-9c89-eff53a37c111"),
                buildingId = DskShrngId("2e07327d-47e7-4da1-9c89-eff53a37c222"),
                employeeId = DskShrngId("2e07327d-47e7-4da1-9c89-eff53a37c000"),
                status = model.DemandStatus.DELETED,
                demandId = DskShrngId("2e07327d-47e7-4da1-9c89-eff53a37cfff"),
                workDeskId = DskShrngId("2e07327d-47e7-4da1-9c89-eff53a37caaa"),
                number = "01/001"
            )
        )

        val response = context.toTransportDemand() as DemandDeleteResponse

        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cdb7", response.requestId)
        assertEquals(ResponseResult.SUCCESS, response.result)
        assertEquals("2022-01-01", response.demand?.date)
        assertEquals("2022-01-10", response.demand?.bookingDate)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37c000", response.demand?.employeeId)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37c111", response.demand?.branchId)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37c222", response.demand?.buildingId)
        assertEquals(DemandStatus.DELETED, response.demand?.status)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cfff", response.demand?.demandId)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37caaa", response.demand?.workDeskId)
        assertEquals("01/001", response.demand?.number)
        assertNull(response.demand?.declineReason)
        assertNull(response.demand?.userId)
        assertNull(response.demand?.lock)

    }
}