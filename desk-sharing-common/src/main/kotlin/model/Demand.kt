package model

import NONE
import kotlinx.datetime.LocalDate

data class Demand (

    val date: LocalDate = LocalDate.NONE,
    val bookingDate: LocalDate = LocalDate.NONE,
    val employeeId: DskShrngId = DskShrngId.NONE,
    val branchId: DskShrngId = DskShrngId.NONE,
    val buildingId: DskShrngId = DskShrngId.NONE,
    val status: DemandStatus = DemandStatus.NONE,
    val number: String = "",
    val workDeskId: DskShrngId = DskShrngId.NONE,
    val declineReason: String = "",
    val demandId: DemandId = DemandId.NONE,
    val userId: DemandUserId = DemandUserId.NONE,
    val lock: String = "",
    val permissions: MutableSet<DemandPermissionClient> = mutableSetOf()

)

