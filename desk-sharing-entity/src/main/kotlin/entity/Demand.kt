package entity

import java.time.LocalDate
import java.util.*

data class Demand(
    var id: UUID?,
    var number: String?,
    var date: LocalDate,
    var employee: Employee,
    var branch: Branch,
    var building: Building,
    var bookingDate: LocalDate,
    var status: DemandStatus,
    var workDesk: WorkDesk?,
    var declineReason: String?
) : BaseEntity(id)
