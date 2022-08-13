import ru.otus.otuskotlin.deskSharing.api.v1.models.*
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class DeleteRequestSerializationTest {
    private val request = DemandDeleteRequest(
       requestId = "2e07327d-47e7-4da1-9c89-eff53a37cdb7",
        debug = DemandDebug(
            mode = DemandRequestDebugMode.STUB,
            stub = DemandRequestDebugStubs.CANNOT_DELETE
        ),
        demand = DemandDeleteObjectDto(
            id = "2e07327d-47e7-4da1-9c89-eff53a37c000",
            lock = "123"
        )
    )

    @Test
    fun serialize() {
        val json = apiV1Mapper.writeValueAsString(request)

        assertContains(json, Regex("\"id\":\\s*\"2e07327d-47e7-4da1-9c89-eff53a37c000\""))
        assertContains(json, Regex("\"lock\":\\s*\"123\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"cannotDelete\""))
        assertContains(json, Regex("\"requestType\":\\s*\"delete\""))
        assertContains(json, Regex("\"requestId\":\\s*\"2e07327d-47e7-4da1-9c89-eff53a37cdb7\""))
    }

    @Test
    fun deserialize() {
        val json = apiV1Mapper.writeValueAsString(request)
        val obj = apiV1Mapper.readValue(json, IRequest::class.java) as DemandDeleteRequest

        assertEquals(request, obj)
    }
}
