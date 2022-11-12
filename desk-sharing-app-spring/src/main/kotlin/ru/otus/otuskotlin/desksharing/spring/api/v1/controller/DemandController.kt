package ru.otus.otuskotlin.desksharing.spring.api.v1.controller

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandCreateRequest
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandCreateResponse
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandDeleteRequest
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandDeleteResponse
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandReadRequest
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandReadResponse
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandSearchRequest
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandSearchResponse
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandUpdateRequest
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandUpdateResponse
import ru.otus.otuskotlin.desksharing.biz.DemandProcessor
import ru.otus.otuskotlin.desksharing.common.model.DemandCommand

@RestController
@RequestMapping("v1/demand")
class DemandController(
    private val processor: DemandProcessor
) {

    @PostMapping("book")
    suspend fun createDemand(@RequestBody request: DemandCreateRequest, @AuthenticationPrincipal principal: Jwt): DemandCreateResponse =
        processV1(processor, DemandCommand.CREATE, request = request, principal = principal)

    @PostMapping("read")
    suspend fun readDemand(@RequestBody request: DemandReadRequest, @AuthenticationPrincipal principal: Jwt): DemandReadResponse =
        processV1(processor, DemandCommand.READ, request = request, principal = principal)

    @PostMapping("update")
    suspend fun updateDemand(@RequestBody request: DemandUpdateRequest, @AuthenticationPrincipal principal: Jwt): DemandUpdateResponse =
        processV1(processor, DemandCommand.UPDATE, request = request, principal = principal)

    @PostMapping("unbook")
    suspend fun deleteDemand(@RequestBody request: DemandDeleteRequest, @AuthenticationPrincipal principal: Jwt): DemandDeleteResponse =
        processV1(processor, DemandCommand.DELETE, request = request, principal = principal)

    @PostMapping("search")
    suspend fun searchDemand(@RequestBody request: DemandSearchRequest, @AuthenticationPrincipal principal: Jwt): DemandSearchResponse =
        processV1(processor, DemandCommand.SEARCH, request = request, principal = principal)

}