package entity

import java.util.*

data class Equipment(
    var id: UUID?,
    var monitorsCount: Int,
    var withPhone: Boolean,
    var withScanner: Boolean,
    var withPrinter: Boolean
) : BaseEntity(id)
