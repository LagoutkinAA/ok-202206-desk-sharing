package model

import java.time.LocalDate
import java.util.*

data class Demand(
    var id: UUID?,
    var date: LocalDate,
    var employee: Employee,
    var branch: Branch,
    var building: Building,
    var bookingDate: LocalDate,
    var equipment: Equipment,
    var withElevator: Boolean = false,
    var withWindow: Boolean = false,
    var status: DemandStatus,
    var workDesk: WorkDesk?,
    var declineReason: String?
) : BaseEntity(id)
