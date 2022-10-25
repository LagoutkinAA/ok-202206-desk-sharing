package ru.otus.otuskotlin.desksharing.common.repository

import ru.otus.otuskotlin.desksharing.common.model.DemandError

interface IDbResponse<T> {
    val data: T?
    val isSuccess: Boolean
    val errors: List<DemandError>
}
