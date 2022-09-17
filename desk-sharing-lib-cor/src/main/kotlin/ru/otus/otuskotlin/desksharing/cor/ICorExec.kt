package ru.otus.otuskotlin.desksharing.cor

interface ICorExec<T> {
    val title: String
    val description: String
    suspend fun exec(context: T)
}