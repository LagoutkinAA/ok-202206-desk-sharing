package ru.otus.otuskotlin.desksharing.validation

interface IValidator<T> {
    infix fun validate(sample: T): ValidationResult
}
