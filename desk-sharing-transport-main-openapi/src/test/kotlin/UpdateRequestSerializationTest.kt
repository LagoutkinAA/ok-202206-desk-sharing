import ru.otus.otuskotlin.deskSharing.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class UpdateRequestSerializationTest {
    private val request = DemandUpdateRequest(
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
            status = DemandStatus.DECLINED,
            declineReason = "No free work desk available",
            demandId = "2e07327d-47e7-4da1-9c89-eff53a37cfff",
            number = "01/001"
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
        assertContains(json, Regex("\"status\":\\s*\"DECLINED\""))
        assertContains(json, Regex("\"declineReason\":\\s*\"No free work desk available\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"success\""))
        assertContains(json, Regex("\"requestType\":\\s*\"update\""))
        assertContains(json, Regex("\"requestId\":\\s*\"2e07327d-47e7-4da1-9c89-eff53a37cdb7\""))
        assertContains(json, Regex("\"demandId\":\\s*\"2e07327d-47e7-4da1-9c89-eff53a37cfff\""))
        assertContains(json, Regex("\"number\":\\s*\"01/001\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as DemandUpdateRequest

        assertEquals(request, obj)
    }
}
