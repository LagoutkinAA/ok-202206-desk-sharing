package model

@JvmInline
value class DemandBuildingId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = DemandBuildingId("")
    }
}