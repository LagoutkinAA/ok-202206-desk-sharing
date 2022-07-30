package model

import java.util.*

data class WorkSpace(
    var id: UUID?,
    var number: String,
    var building: Building,
    var flow: Int,
    var hasWindow: Boolean
) : BaseEntity(id)
