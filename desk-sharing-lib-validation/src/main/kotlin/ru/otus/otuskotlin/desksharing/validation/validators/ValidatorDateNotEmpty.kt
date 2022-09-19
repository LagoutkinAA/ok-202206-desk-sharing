package ru.otus.otuskotlin.desksharing.validation.validators

import NONE
import kotlinx.datetime.LocalDate
import ru.otus.otuskotlin.desksharing.validation.IValidator
import ru.otus.otuskotlin.desksharing.validation.ValidationFieldError
import ru.otus.otuskotlin.desksharing.validation.ValidationResult

class ValidatorDateNotEmpty(
    private val field: String = "",
    private val message: String = "Date in field $field must not be empty"
) : IValidator<LocalDate> {

    override fun validate(sample: LocalDate): ValidationResult {
        return if (sample == LocalDate.NONE) {
            ValidationResult(
                errors = listOf(
                    ValidationFieldError(
                        field = field,
                        message = message,
                    )
                )
            )
        } else {
            ValidationResult.SUCCESS
        }
    }

}