package ru.otus.otuskotlin.desksharing.spring.api.v1.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.otuskotlin.deskSharing.api.v1.models.*
import ru.otus.otuskotlin.desksharing.biz.DemandProcessor
import ru.otus.otuskotlin.desksharing.common.model.DemandCommand

@RestController
@RequestMapping("v1/demand")
class DemandController(
    private val processor: DemandProcessor
) {

    @PostMapping("book")
    suspend fun createDemand(@RequestBody request: DemandCreateRequest): DemandCreateResponse =
        processV1(processor, DemandCommand.CREATE, request = request)

    @PostMapping("read")
    suspend fun readDemand(@RequestBody request: DemandReadRequest): DemandReadResponse =
        processV1(processor, DemandCommand.READ, request = request)

    @PostMapping("update")
    suspend fun updateDemand(@RequestBody request: DemandUpdateRequest): DemandUpdateResponse =
        processV1(processor, DemandCommand.UPDATE, request = request)

    @PostMapping("unbook")
    suspend fun deleteDemand(@RequestBody request: DemandDeleteRequest): DemandDeleteResponse =
        processV1(processor, DemandCommand.DELETE, request = request)

    @PostMapping("search")
    suspend fun searchDemand(@RequestBody request: DemandSearchRequest): DemandSearchResponse =
        processV1(processor, DemandCommand.SEARCH, request = request)

}