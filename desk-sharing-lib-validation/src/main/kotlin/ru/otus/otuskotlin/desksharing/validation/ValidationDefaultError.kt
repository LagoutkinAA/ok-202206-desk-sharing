package ru.otus.otuskotlin.desksharing.validation

data class ValidationDefaultError(
    override val message: String,
) : IValidationError
