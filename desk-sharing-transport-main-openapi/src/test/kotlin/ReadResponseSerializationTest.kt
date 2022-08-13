import ru.otus.otuskotlin.deskSharing.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ReadResponseSerializationTest {
    private val request = DemandReadResponse(
        requestId = "2e07327d-47e7-4da1-9c89-eff53a37cdb7",
        result = ResponseResult.SUCCESS,
        demand = DemandResponseObjectDto(
            date = "2022-01-01",
            bookingDate = "2022-01-10",
            employeeId = "2e07327d-47e7-4da1-9c89-eff53a37c000",
            branchId = "2e07327d-47e7-4da1-9c89-eff53a37c111",
            buildingId = "2e07327d-47e7-4da1-9c89-eff53a37c222",
            status = DemandStatus.ACCEPTED,
            demandId = "2e07327d-47e7-4da1-9c89-eff53a37cfff",
            number = "01/001",
            workDeskId = "2e07327d-47e7-4da1-9c89-eff53a37caaa"
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
        assertContains(json, Regex("\"status\":\\s*\"ACCEPTED\""))
        assertContains(json, Regex("\"demandId\":\\s*\"2e07327d-47e7-4da1-9c89-eff53a37cfff\""))
        assertContains(json, Regex("\"number\":\\s*\"01/001\""))
        assertContains(json, Regex("\"responseType\":\\s*\"read\""))
        assertContains(json, Regex("\"requestId\":\\s*\"2e07327d-47e7-4da1-9c89-eff53a37cdb7\""))
        assertContains(json, Regex("\"workDeskId\":\\s*\"2e07327d-47e7-4da1-9c89-eff53a37caaa\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as DemandReadResponse

        assertEquals(request, obj)
    }
}
