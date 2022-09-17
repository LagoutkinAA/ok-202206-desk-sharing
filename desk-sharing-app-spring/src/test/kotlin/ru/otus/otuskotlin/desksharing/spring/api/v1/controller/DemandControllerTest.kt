package ru.otus.otuskotlin.desksharing.spring.api.v1.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coVerify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import ru.otus.otuskotlin.deskSharing.api.v1.models.*
import ru.otus.otuskotlin.desksharing.biz.DemandProcessor

@WebFluxTest(DemandController::class)
internal class DemandControllerTest {

    @Autowired
    private lateinit var mvc: WebTestClient

    @Autowired
    private lateinit var mapper: ObjectMapper

    @MockkBean(relaxUnitFun = true)
    private lateinit var processor: DemandProcessor

    @Test
    fun createDemand() {
        val request =
            DemandCreateRequest(debug = DemandDebug(DemandRequestDebugMode.STUB, DemandRequestDebugStubs.SUCCESS))

        mvc.post()
            .uri("/v1/demand/book")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(request))
            .exchange()
            .expectStatus().isOk
            .expectBody(DemandCreateResponse::class.java)
            .value {
                println("$it")
                assertThat(it.responseType).isEqualTo("create")
            }

        coVerify { processor.exec(any()) }
    }

    @Test
    fun readDemand() {
        val request =
            DemandReadRequest(
                debug = DemandDebug(
                    DemandRequestDebugMode.STUB,
                    DemandRequestDebugStubs.SUCCESS
                )
            )


        mvc.post()
            .uri("/v1/demand/read")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue(request)
            )
            .exchange()
            .expectStatus().isOk
            .expectBody(DemandReadResponse::class.java)
            .value {
                println("$it")
                assertThat(it.responseType).isEqualTo("read")
            }

        coVerify { processor.exec(any()) }
    }

    @Test
    fun updateDemand() {
        val request =
            DemandUpdateRequest(
                debug = DemandDebug(
                    DemandRequestDebugMode.STUB,
                    DemandRequestDebugStubs.SUCCESS
                )
            )

        mvc.post()
            .uri("/v1/demand/update")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue(request)
            )
            .exchange()
            .expectStatus().isOk
            .expectBody(DemandUpdateResponse::class.java)
            .value {
                println("$it")
                assertThat(it.responseType).isEqualTo("update")
            }

        coVerify { processor.exec(any()) }
    }

    @Test
    fun deleteDemand() {
        val request =
            DemandDeleteRequest(
                debug = DemandDebug(
                    DemandRequestDebugMode.STUB,
                    DemandRequestDebugStubs.SUCCESS
                )
            )

        mvc.post()
            .uri("/v1/demand/unbook")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue(request)
            )
            .exchange()
            .expectStatus().isOk
            .expectBody(DemandDeleteResponse::class.java)
            .value {
                println("$it")
                assertThat(it.responseType).isEqualTo("delete")
            }

        coVerify { processor.exec(any()) }
    }

    @Test
    fun searchDemand() {
        val request =
            DemandSearchRequest(
                debug = DemandDebug(
                    DemandRequestDebugMode.STUB,
                    DemandRequestDebugStubs.SUCCESS
                )
            )

        mvc.post()
            .uri("/v1/demand/search")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue(request)
            )
            .exchange()
            .expectStatus().isOk
            .expectBody(DemandSearchResponse::class.java)
            .value {
                println("$it")
                assertThat(it.responseType).isEqualTo("search")
            }

        coVerify { processor.exec(any()) }
    }

}