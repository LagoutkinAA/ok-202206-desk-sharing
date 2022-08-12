package model

import NONE
import kotlinx.datetime.LocalDate

data class Demand (

    var date: LocalDate = LocalDate.NONE,
    var bookingDate: LocalDate = LocalDate.NONE,
    var employeeId: DskShrngId = DskShrngId.NONE,
    var branchId: DskShrngId = DskShrngId.NONE,
    var buildingId: DskShrngId = DskShrngId.NONE,
    var status: DemandStatus = DemandStatus.NONE,
    var number: String = "",
    var workDeskId: DskShrngId = DskShrngId.NONE,
    var declineReason: String = "",
    var demandId: DskShrngId = DskShrngId.NONE,
    var userId: DemandUserId = DemandUserId.NONE,
    var lock: String = "",
    val permissions: MutableSet<DemandPermissionClient> = mutableSetOf()

)

