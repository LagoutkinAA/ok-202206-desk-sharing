package ru.otus.otuskotlin.desksharing.common.repository

import ru.otus.otuskotlin.desksharing.common.model.DemandDto

data class DbDemandRequest(
    val demand: DemandDto
)
