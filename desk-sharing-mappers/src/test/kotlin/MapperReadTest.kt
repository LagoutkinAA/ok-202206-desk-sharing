import kotlinx.datetime.LocalDate
import model.*
import org.junit.Test
import ru.otus.otuskotlin.deskSharing.api.v1.models.*
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandStatus
import stubs.DemandStubs
import kotlin.test.assertEquals
import kotlin.test.assertNull

class MapperReadTest {

    @Test
    fun fromTransport() {
        val request = DemandReadRequest(
            requestId = "2e07327d-47e7-4da1-9c89-eff53a37cdb7",
            debug = DemandDebug(
                mode = DemandRequestDebugMode.STUB,
                stub = DemandRequestDebugStubs.SUCCESS
            ),
            demand = DemandReadObjectDto(
                demandId = "2e07327d-47e7-4da1-9c89-eff53a37cfff"
            )
        )

        val context = DemandContext()
        context.fromTransport(request)

        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cdb7", context.requestId.asString())
        assertEquals(DemandStubs.SUCCESS, context.stubCase)
        assertEquals(DemandCommand.READ, context.command)
        assertEquals(DemandState.NONE, context.state)
        assertEquals(DskShrngWorkMode.STUB, context.workMode)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cfff", context.demandRequest.demandId.asString())
    }

    @Test
    fun toTransport() {
        val context = DemandContext(
            requestId = DemandRequestId("2e07327d-47e7-4da1-9c89-eff53a37cdb7"),
            command = DemandCommand.READ,
            state = DemandState.RUNNING,
            workMode = DskShrngWorkMode.STUB,
            stubCase = DemandStubs.SUCCESS,
            demandResponse = DemandDto(
                date = LocalDate(2022, 1, 1),
                bookingDate = LocalDate(2022, 1, 10),
                employeeId = DskShrngId("2e07327d-47e7-4da1-9c89-eff53a37c000"),
                status = model.DemandStatus.NEW,
                demandId = DskShrngId("2e07327d-47e7-4da1-9c89-eff53a37cfff"),
                workDeskNumber = WorkDeskNumber("1/1"),
                number = "01/001"
            )
        )

        val response = context.toTransportDemand() as DemandReadResponse

        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cdb7", response.requestId)
        assertEquals(ResponseResult.SUCCESS, response.result)
        assertEquals("2022-01-01", response.demand?.date)
        assertEquals("2022-01-10", response.demand?.bookingDate)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37c000", response.demand?.employeeId)
        assertEquals(DemandStatus.NEW, response.demand?.status)
        assertEquals("2e07327d-47e7-4da1-9c89-eff53a37cfff", response.demand?.demandId)
        assertEquals("1/1", response.demand?.workDeskNumber)
        assertEquals("01/001", response.demand?.number)
        assertNull(response.demand?.declineReason)
        assertNull(response.demand?.userId)
        assertNull(response.demand?.lock)

    }
}