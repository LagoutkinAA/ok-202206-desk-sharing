package ru.otus.otuskotlin.desksharing.common.repository

import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId

data class DbDemandIdRequest(
    val id: DskShrngId,
    val lock: String = ""
) {
    constructor(demand: DemandDto): this(demand.demandId, demand.lock)
}
