package ru.otus.otuskotlin.desksharing.common.model

import kotlinx.datetime.LocalDate
import ru.otus.otuskotlin.desksharing.common.NONE

data class DemandFilter(
    val dateFrom: LocalDate = LocalDate.NONE,
    val dateTo: LocalDate = LocalDate.NONE,
    val employeeId: DskShrngId = DskShrngId.NONE
)
