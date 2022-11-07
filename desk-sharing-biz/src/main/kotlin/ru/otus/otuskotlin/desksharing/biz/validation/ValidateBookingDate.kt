package ru.otus.otuskotlin.desksharing.biz.validation

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.NONE
import ru.otus.otuskotlin.desksharing.common.helpers.errorValidation
import ru.otus.otuskotlin.desksharing.common.helpers.fail
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.worker

fun ICorChainDsl<DemandContext>.validateBookingDateNotEmpty(title: String) = worker {
    this.title = title
    on { demandRequestValidating.bookingDate == LocalDate.NONE }
    handle {
        fail(
            errorValidation(
                field = "bookingDate",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}

fun ICorChainDsl<DemandContext>.validateBookingDateInRange(title: String) = worker {
    this.title = title
    val now = java.time.LocalDate.now()
    val dateFrom = LocalDate(now.year, now.month, now.dayOfMonth).plus(DatePeriod(days = 1))
    val dateTo = dateFrom.plus(DatePeriod(months = 1))

    on { demandRequestValidating.bookingDate !in dateFrom..dateTo }
    handle {
        fail(
            errorValidation(
                field = "bookingDate",
                violationCode = "out of range",
                description = "field must be between [$dateFrom, $dateTo]"
            )
        )
    }
}
