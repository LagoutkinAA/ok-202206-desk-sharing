package ru.otus.otuskotlin.desksharing.spring.api.v1.controller

import fromTransport
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.otus.otuskotlin.deskSharing.api.v1.models.*
import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.stub.DeskSharingDemandStub
import toTransportCreate
import toTransportDelete
import toTransportRead
import toTransportSearch
import toTransportUpdate

@RestController
@RequestMapping("v1/demand")
class DemandController {

    @PostMapping("book")
    fun createDemand(@RequestBody request: DemandCreateRequest): DemandCreateResponse {
        val context = DemandContext()
        context.fromTransport(request)
        context.demandResponse = DeskSharingDemandStub.get()
        return context.toTransportCreate()
    }

    @PostMapping("read")
    fun readDemand(@RequestBody request: DemandReadRequest): DemandReadResponse {
        val context = DemandContext()
        context.fromTransport(request)
        context.demandResponse = DeskSharingDemandStub.get()
        return context.toTransportRead()
    }

    @PostMapping("update")
    fun updateDemand(@RequestBody request: DemandUpdateRequest): DemandUpdateResponse {
        val context = DemandContext()
        context.fromTransport(request)
        context.demandResponse = DeskSharingDemandStub.get()
        return context.toTransportUpdate()
    }

    @PostMapping("unbook")
    fun deleteDemand(@RequestBody request: DemandDeleteRequest): DemandDeleteResponse {
        val context = DemandContext()
        context.fromTransport(request)
        context.demandResponse = DeskSharingDemandStub.get()
        return context.toTransportDelete()
    }

    @PostMapping("search")
    fun searchDemand(@RequestBody request: DemandSearchRequest): DemandSearchResponse {
        val context = DemandContext()
        context.fromTransport(request)
        context.demandResponses.add(DeskSharingDemandStub.get())
        return context.toTransportSearch()
    }

}