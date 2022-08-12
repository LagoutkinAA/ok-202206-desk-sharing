package entity

import java.util.*

data class WorkDesk(
    var id: UUID?,
    var number: String,
    var workSpace: WorkSpace,
    var booking: Booking
) : BaseEntity(id)
