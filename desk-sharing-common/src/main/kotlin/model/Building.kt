package model

import java.util.*

data class Building(
    var id: UUID?,
    var number: String,
    var branch: Branch,
    var flows: Int,
    var hasElevator: Boolean
) : BaseEntity(id)
