package ru.otus.otuskotlin.desksharing.validation

interface IValidationFieldError : IValidationError {
    val field: String
}
