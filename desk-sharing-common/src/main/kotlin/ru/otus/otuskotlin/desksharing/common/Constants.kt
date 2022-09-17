import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

private val INSTANT_NONE = Instant.fromEpochMilliseconds(Long.MIN_VALUE)
private val DATE_NONE = LocalDate(1970, 1, 1)


val Instant.Companion.NONE
    get() = INSTANT_NONE

val LocalDate.Companion.NONE
    get() = DATE_NONE

fun LocalDate.Companion.now(): LocalDate {
    val now = java.time.LocalDate.now()
    return LocalDate(now.year, now.month, now.dayOfMonth)
}