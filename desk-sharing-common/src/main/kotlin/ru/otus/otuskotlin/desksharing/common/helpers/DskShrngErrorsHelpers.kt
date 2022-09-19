package ru.otus.otuskotlin.desksharing.common.helpers

import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandError
import ru.otus.otuskotlin.desksharing.common.model.DemandState


fun Throwable.asDemandError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = DemandError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun DemandContext.addError(vararg error: DemandError) = errors.addAll(error)

fun DemandContext.fail(vararg error: DemandError) {
    addError(*error)
    state = DemandState.FAILING
}

fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String
) = DemandError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
)

fun errorMapping(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String
) = DemandError(
    code = "mapping-$field-$violationCode",
    field = field,
    group = "mapping",
    message = "Mapping error for field $field: $description",
)
