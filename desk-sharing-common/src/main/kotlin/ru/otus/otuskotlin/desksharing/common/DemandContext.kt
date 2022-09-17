package ru.otus.otuskotlin.desksharing.common

import NONE
import kotlinx.datetime.Instant
import ru.otus.otuskotlin.desksharing.common.model.*
import ru.otus.otuskotlin.desksharing.common.stubs.DemandStubs

data class DemandContext(
    var command: DemandCommand = DemandCommand.NONE,
    var state: DemandState = DemandState.NONE,
    val errors: MutableList<DemandError> = mutableListOf(),

    var workMode: DskShrngWorkMode = DskShrngWorkMode.PROD,
    var stubCase: DemandStubs = DemandStubs.NONE,

    var requestId: DemandRequestId = DemandRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var demandRequest: DemandDto = DemandDto(),
    var demandFilterRequest: DemandFilter = DemandFilter(),
    var demandResponse: DemandDto = DemandDto(),
    var demandResponses: MutableList<DemandDto> = mutableListOf()
)
