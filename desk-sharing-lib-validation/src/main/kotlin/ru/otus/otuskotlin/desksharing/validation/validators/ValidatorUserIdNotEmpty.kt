package ru.otus.otuskotlin.desksharing.validation.validators

import ru.otus.otuskotlin.desksharing.common.model.DemandUserId
import ru.otus.otuskotlin.desksharing.validation.IValidator
import ru.otus.otuskotlin.desksharing.validation.ValidationFieldError
import ru.otus.otuskotlin.desksharing.validation.ValidationResult

class ValidatorUserIdNotEmpty(
    private val field: String = "",
    private val message: String = "User id in field $field must not be empty"
) : IValidator<DemandUserId> {

    override fun validate(sample: DemandUserId): ValidationResult {
        return if (sample == DemandUserId.NONE) {
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