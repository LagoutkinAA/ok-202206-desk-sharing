package ru.otus.otuskotlin.desksharing.common.repository

import kotlinx.datetime.LocalDate
import ru.otus.otuskotlin.desksharing.common.NONE
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId


data class DbDemandFilterRequest(
    val dateFrom: LocalDate = LocalDate.NONE,
    val dateTo: LocalDate = LocalDate.NONE,
    val employeeId: DskShrngId = DskShrngId.NONE
)
