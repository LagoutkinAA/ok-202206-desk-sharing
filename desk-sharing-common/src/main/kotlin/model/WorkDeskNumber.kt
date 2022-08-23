package model

@JvmInline
value class WorkDeskNumber(private val number: String) {

    fun asString() = number

    companion object {
        val NONE = WorkDeskNumber("")
    }
}