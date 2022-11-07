package ru.otus.otuskotlin.desksharing.validation

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import org.junit.Test
import ru.otus.otuskotlin.desksharing.common.NONE
import ru.otus.otuskotlin.desksharing.common.model.DemandUserId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.now
import ru.otus.otuskotlin.desksharing.validation.validators.ValidatorDateNotEmpty
import ru.otus.otuskotlin.desksharing.validation.validators.ValidatorIdNotEmpty
import ru.otus.otuskotlin.desksharing.validation.validators.ValidatorInRange
import ru.otus.otuskotlin.desksharing.validation.validators.ValidatorUserIdNotEmpty
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ValidationTest {

    @Test
    fun inRange() {
        val dateFrom = LocalDate.now().plus(DatePeriod(days = 1))
        val dateTo = dateFrom.plus(DatePeriod(months = 1))

        val validator = ValidatorInRange<LocalDate>("bookingDate", dateFrom, dateTo)
        val result = validator.validate(LocalDate.now().plus(DatePeriod(days = 2)))

        assertEquals(true, result.isSuccess)
    }

    @Test
    fun outRange() {
        val dateFrom = LocalDate.now().plus(DatePeriod(days = 1))
        val dateTo = dateFrom.plus(DatePeriod(months = 1))

        val validator = ValidatorInRange<LocalDate>("bookingDate", dateFrom, dateTo)
        val result = validator.validate(LocalDate.now())

        assertEquals(false, result.isSuccess)
        assertContains(result.errors.firstOrNull()?.message ?: "", "bookingDate")
    }

    @Test
    fun emptyDate() {
        val validator = ValidatorDateNotEmpty("bookingDate")
        val result = validator.validate(LocalDate.NONE)

        assertEquals(false, result.isSuccess)
        assertContains(result.errors.firstOrNull()?.message ?: "", "bookingDate")
    }

    @Test
    fun emptyId() {
        val validator = ValidatorIdNotEmpty("employeeId")
        val result = validator.validate(DskShrngId.NONE)

        assertEquals(false, result.isSuccess)
        assertContains(result.errors.firstOrNull()?.message ?: "", "employeeId")
    }

    @Test
    fun emptyUserID() {
        val validator = ValidatorUserIdNotEmpty("userId")
        val result = validator.validate(DemandUserId.NONE)

        assertEquals(false, result.isSuccess)
        assertContains(result.errors.firstOrNull()?.message ?: "", "userId")
    }
}