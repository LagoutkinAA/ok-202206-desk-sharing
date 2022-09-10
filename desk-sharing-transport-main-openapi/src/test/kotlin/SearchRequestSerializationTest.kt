import ru.otus.otuskotlin.deskSharing.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class SearchRequestSerializationTest {
    private val request = DemandSearchRequest(
       requestId = "2e07327d-47e7-4da1-9c89-eff53a37cdb7",
        debug = DemandDebug(
            mode = DemandRequestDebugMode.STUB,
            stub = DemandRequestDebugStubs.NOT_FOUND
        ),
        demandFilter = DemandSearchFilter(
            dateFrom = "2022-04-01",
            dateTo = "2022-05-01",
            employeeId = "2e07327d-47e7-4da1-9c89-eff53a37c000"
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"employeeId\":\\s*\"2e07327d-47e7-4da1-9c89-eff53a37c000\""))
        assertContains(json, Regex("\"dateFrom\":\\s*\"2022-04-01\""))
        assertContains(json, Regex("\"dateTo\":\\s*\"2022-05-01\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"notFound\""))
        assertContains(json, Regex("\"requestType\":\\s*\"search\""))
        assertContains(json, Regex("\"requestId\":\\s*\"2e07327d-47e7-4da1-9c89-eff53a37cdb7\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as DemandSearchRequest

        assertEquals(request, obj)
    }
}
