package ru.otus.otuskotlin.desksharing.common.model

data class DemandError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null,
)
