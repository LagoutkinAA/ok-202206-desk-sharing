package ru.otus.otuskotlin.desksharing.spring.api.v1.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import ru.otus.otuskotlin.deskSharing.api.v1.models.*
import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.stub.DeskSharingDemandStub
import toTransportCreate
import toTransportDelete
import toTransportRead
import toTransportSearch
import toTransportUpdate

@WebMvcTest(DemandController::class)
internal class DemandControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Test
    fun createDemand() {
        val request = mapper.writeValueAsString(DemandCreateRequest(debug = DemandDebug(DemandRequestDebugMode.STUB, DemandRequestDebugStubs.SUCCESS)))
        val response = mapper.writeValueAsString(
            DemandContext().apply { demandResponse = DeskSharingDemandStub.get() }.toTransportCreate()
        )

        mvc.perform(
            MockMvcRequestBuilders.post("/v1/demand/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(response))
    }

    @Test
    fun readDemand() {
        val request = mapper.writeValueAsString(DemandReadRequest(debug = DemandDebug(DemandRequestDebugMode.STUB, DemandRequestDebugStubs.SUCCESS)))
        val response = mapper.writeValueAsString(
            DemandContext().apply { demandResponse = DeskSharingDemandStub.get() }.toTransportRead()
        )

        mvc.perform(
            MockMvcRequestBuilders.post("/v1/demand/read")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(response))
    }

    @Test
    fun updateDemand() {
        val request = mapper.writeValueAsString(DemandUpdateRequest(debug = DemandDebug(DemandRequestDebugMode.STUB, DemandRequestDebugStubs.SUCCESS)))
        val response = mapper.writeValueAsString(
            DemandContext().apply { demandResponse = DeskSharingDemandStub.get() }.toTransportUpdate()
        )

        mvc.perform(
            MockMvcRequestBuilders.post("/v1/demand/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(response))
    }

    @Test
    fun deleteDemand() {
        val request = mapper.writeValueAsString(DemandDeleteRequest(debug = DemandDebug(DemandRequestDebugMode.STUB, DemandRequestDebugStubs.SUCCESS)))
        val response = mapper.writeValueAsString(
            DemandContext().apply { demandResponse = DeskSharingDemandStub.get() }.toTransportDelete()
        )

        mvc.perform(
            MockMvcRequestBuilders.post("/v1/demand/unbook")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(response))
    }

    @Test
    fun searchDemand() {
        val request = mapper.writeValueAsString(DemandSearchRequest(debug = DemandDebug(DemandRequestDebugMode.STUB, DemandRequestDebugStubs.SUCCESS)))
        val response = mapper.writeValueAsString(
            DemandContext().apply { demandResponses = mutableListOf(DeskSharingDemandStub.get()) }.toTransportSearch()
        )

        mvc.perform(
            MockMvcRequestBuilders.post("/v1/demand/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(response))
    }

}