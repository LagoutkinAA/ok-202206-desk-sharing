package ru.otus.otuskotlin.desksharing.validation

interface IValidationExceptionError: IValidationError {
    val exception: Throwable
}
