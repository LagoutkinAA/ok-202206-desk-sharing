import kotlinx.datetime.LocalDate
import model.*
import org.junit.Test
import ru.otus.otuskotlin.deskSharing.api.v1.models.*
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandStatus
import stubs.DemandStubs
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MapperSearchTest {

    @Test
    fun fromTransport() {
        val request = DemandSearchRequest(
            requestId = "2e07327d-47e7-4da1-9c89-eff53a37cdb7",
            debug = DemandDebug(
                mode = DemandRequestDebugMode.STUB,
                stub = DemandRequestDebugStubs.SUCCESS
            ),
            demandFilter = DemandSearchFilter(
                dateFrom = "2022-04-01",
                dateTo = "2022-05-01",
                employeeId = "2e07327d-47e7-4da1-9c89-eff53a37c000"
            )
        )

        val context = DemandContext()
        context.fromTransport(request)

        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cdb7", context.requestId.asString())
        assertEquals(DemandStubs.SUCCESS, context.stubCase)
        assertEquals(DemandCommand.SEARCH, context.command)
        assertEquals(DemandState.NONE, context.state)
        assertEquals(DskShrngWorkMode.STUB, context.workMode)
        assertEquals(LocalDate(2022, 4, 1), context.demandFilterRequest.dateFrom)
        assertEquals(LocalDate(2022, 5, 1), context.demandFilterRequest.dateTo)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37c000", context.demandFilterRequest.employeeId.asString())
    }

    @Test
    fun toTransport() {
        val context = DemandContext(
            requestId = DemandRequestId("2e07327d-47e7-4da1-9c89-eff53a37cdb7"),
            command = DemandCommand.SEARCH,
            state = DemandState.RUNNING,
            workMode = DskShrngWorkMode.STUB,
            stubCase = DemandStubs.SUCCESS,
            demandResponses = mutableListOf(
                DemandDto(
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
        )

        val response = context.toTransportDemand() as DemandSearchResponse

        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cdb7", response.requestId)
        assertEquals(ResponseResult.SUCCESS, response.result)
        assertEquals("2022-01-01", response.demands?.firstOrNull()?.date)
        assertEquals("2022-01-10", response.demands?.firstOrNull()?.bookingDate)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37c000", response.demands?.firstOrNull()?.employeeId)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37c111", response.demands?.firstOrNull()?.branchId)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37c222", response.demands?.firstOrNull()?.buildingId)
        assertEquals(DemandStatus.ACCEPTED, response.demands?.firstOrNull()?.status)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cfff", response.demands?.firstOrNull()?.demandId)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37caaa", response.demands?.firstOrNull()?.workDeskId)
        assertEquals("01/001", response.demands?.firstOrNull()?.number)
        assertNull(response.demands?.firstOrNull()?.declineReason)
        assertNull(response.demands?.firstOrNull()?.userId)
        assertNull(response.demands?.firstOrNull()?.lock)

    }
}