package ru.otus.otuskotlin.desksharing.common.model

import NONE
import kotlinx.datetime.LocalDate

data class DemandFilter(
    val dateFrom: LocalDate = LocalDate.NONE,
    val dateTo: LocalDate = LocalDate.NONE,
    val employeeId: DskShrngId = DskShrngId.NONE
)
