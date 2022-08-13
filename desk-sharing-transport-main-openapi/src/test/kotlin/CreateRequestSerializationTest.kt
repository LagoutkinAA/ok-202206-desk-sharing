import ru.otus.otuskotlin.deskSharing.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class CreateRequestSerializationTest {
    private val request = DemandCreateRequest(
       requestId = "2e07327d-47e7-4da1-9c89-eff53a37cdb7",
        debug = DemandDebug(
            mode = DemandRequestDebugMode.STUB,
            stub = DemandRequestDebugStubs.SUCCESS
        ),
        demand = DemandCreateObjectDto(
            date = "2022-01-01",
            bookingDate = "2022-01-10",
            employeeId = "2e07327d-47e7-4da1-9c89-eff53a37c000",
            branchId = "2e07327d-47e7-4da1-9c89-eff53a37c111",
            buildingId = "2e07327d-47e7-4da1-9c89-eff53a37c222",
            status = DemandStatus.NEW
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"employeeId\":\\s*\"2e07327d-47e7-4da1-9c89-eff53a37c000\""))
        assertContains(json, Regex("\"branchId\":\\s*\"2e07327d-47e7-4da1-9c89-eff53a37c111\""))
        assertContains(json, Regex("\"buildingId\":\\s*\"2e07327d-47e7-4da1-9c89-eff53a37c222\""))
        assertContains(json, Regex("\"date\":\\s*\"2022-01-01\""))
        assertContains(json, Regex("\"bookingDate\":\\s*\"2022-01-10\""))
        assertContains(json, Regex("\"status\":\\s*\"NEW\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"success\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
        assertContains(json, Regex("\"requestId\":\\s*\"2e07327d-47e7-4da1-9c89-eff53a37cdb7\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as DemandCreateRequest

        assertEquals(request, obj)
    }
}
