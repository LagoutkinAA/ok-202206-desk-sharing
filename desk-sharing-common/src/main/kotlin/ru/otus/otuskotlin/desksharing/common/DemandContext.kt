package ru.otus.otuskotlin.desksharing.common

import kotlinx.datetime.Instant
import ru.otus.otuskotlin.desksharing.common.model.DemandCommand
import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DemandError
import ru.otus.otuskotlin.desksharing.common.model.DemandFilter
import ru.otus.otuskotlin.desksharing.common.model.DemandRequestId
import ru.otus.otuskotlin.desksharing.common.model.DemandSettings
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.model.DskShrngWorkMode
import ru.otus.otuskotlin.desksharing.common.repository.IDemandRepository
import ru.otus.otuskotlin.desksharing.common.stubs.DemandStubs

data class DemandContext(
    var settings: DemandSettings = DemandSettings(),

    var command: DemandCommand = DemandCommand.NONE,
    var state: DemandState = DemandState.NONE,
    val errors: MutableList<DemandError> = mutableListOf(),

    var workMode: DskShrngWorkMode = DskShrngWorkMode.PROD,
    var stubCase: DemandStubs = DemandStubs.NONE,

    var demandRepo: IDemandRepository = IDemandRepository.NONE,

    var requestId: DemandRequestId = DemandRequestId.NONE,
    var timeStart: Instant = Instant.NONE,

    var demandRequest: DemandDto = DemandDto(),
    var demandFilterRequest: DemandFilter = DemandFilter(),

    var demandRequestValidating: DemandDto = DemandDto(),
    var demandFilterRequestValidating: DemandFilter = DemandFilter(),

    var demandRequestValidated: DemandDto = DemandDto(),
    var demandFilterRequestValidated: DemandFilter = DemandFilter(),

    var demandRepoRead: DemandDto = DemandDto(),
    var demandRepoPrepare: DemandDto = DemandDto(),
    var demandRepoDone: DemandDto = DemandDto(),
    var demandsRepoDone: MutableList<DemandDto> = mutableListOf(),

    var demandResponse: DemandDto = DemandDto(),
    var demandsResponse: MutableList<DemandDto> = mutableListOf()
)
