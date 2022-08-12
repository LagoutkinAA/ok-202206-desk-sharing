package model

@JvmInline
value class DemandId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = DemandId("")
    }
}
