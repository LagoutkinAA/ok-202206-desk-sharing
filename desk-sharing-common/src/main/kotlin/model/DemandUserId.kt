package model

@JvmInline
value class DemandUserId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = DemandUserId("")
    }
}
