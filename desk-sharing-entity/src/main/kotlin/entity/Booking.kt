package entity

import java.time.LocalDate
import java.util.*

data class Booking(
    var id: UUID?,
    var number: String,
    var date: LocalDate,
    var workDeskId: UUID,
    var status: BookingStatus
) : BaseEntity(id)
