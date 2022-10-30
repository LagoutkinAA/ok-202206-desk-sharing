package ru.otus.otuskotlin.desksharing.common.model

@JvmInline
value class WorkDeskNumber(private val number: String) {

    fun asString() = number

    fun asInt() = number.toInt()

    companion object {
        val NONE = WorkDeskNumber("")
    }
}