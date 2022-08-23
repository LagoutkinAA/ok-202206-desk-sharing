import ru.otus.otuskotlin.deskSharing.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class DeleteResponseSerializationTest {
    private val request = DemandDeleteResponse(
        requestId = "2e07327d-47e7-4da1-9c89-eff53a37cdb7",
        result = ResponseResult.SUCCESS,
        demand = DemandResponseObjectDto(
            date = "2022-01-01",
            bookingDate = "2022-01-10",
            employeeId = "2e07327d-47e7-4da1-9c89-eff53a37c000",
            status = DemandStatus.DELETED,
            demandId = "2e07327d-47e7-4da1-9c89-eff53a37cfff",
            number = "01/001",
            workDeskNumber = "2e07327d-47e7-4da1-9c89-eff53a37caaa"
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"employeeId\":\\s*\"2e07327d-47e7-4da1-9c89-eff53a37c000\""))
        assertContains(json, Regex("\"date\":\\s*\"2022-01-01\""))
        assertContains(json, Regex("\"bookingDate\":\\s*\"2022-01-10\""))
        assertContains(json, Regex("\"status\":\\s*\"DELETED\""))
        assertContains(json, Regex("\"demandId\":\\s*\"2e07327d-47e7-4da1-9c89-eff53a37cfff\""))
        assertContains(json, Regex("\"number\":\\s*\"01/001\""))
        assertContains(json, Regex("\"responseType\":\\s*\"delete\""))
        assertContains(json, Regex("\"requestId\":\\s*\"2e07327d-47e7-4da1-9c89-eff53a37cdb7\""))
        assertContains(json, Regex("\"workDeskNumber\":\\s*\"2e07327d-47e7-4da1-9c89-eff53a37caaa\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IResponse::class.java) as DemandDeleteResponse

        assertEquals(request, obj)
    }
}
