package ru.otus.otuskotlin.desksharing.validation.validators

import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.validation.IValidator
import ru.otus.otuskotlin.desksharing.validation.ValidationFieldError
import ru.otus.otuskotlin.desksharing.validation.ValidationResult

class ValidatorIdNotEmpty(
    private val field: String = "",
    private val message: String = "Id in field $field must not be empty"
) : IValidator<DskShrngId> {

    override fun validate(sample: DskShrngId): ValidationResult {
        return if (sample == DskShrngId.NONE) {
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