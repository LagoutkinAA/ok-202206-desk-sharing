package ru.otus.otuskotlin.desksharing.common

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

private val INSTANT_NONE = Instant.fromEpochMilliseconds(Long.MIN_VALUE)
private val DATE_NONE = LocalDate(1970, 1, 1)
private val DATE_MAX = LocalDate(3000, 12, 31)


val Instant.Companion.NONE
    get() = INSTANT_NONE

val LocalDate.Companion.NONE
    get() = DATE_NONE

val LocalDate.Companion.MAX
    get() = DATE_MAX

fun LocalDate.Companion.now(): LocalDate {
    val now = java.time.LocalDate.now()
    return LocalDate(now.year, now.month, now.dayOfMonth)
}

fun LocalDate.Companion.toJavaLocalDate(source: LocalDate): java.time.LocalDate? {
    return if (source == LocalDate.NONE) null else java.time.LocalDate.of(source.year, source.month, source.dayOfMonth)
}

fun LocalDate.Companion.fromJavaLocalDate(source: java.time.LocalDate?): LocalDate {
    return if (source == null) LocalDate.NONE else LocalDate(source.year, source.month, source.dayOfMonth)
}