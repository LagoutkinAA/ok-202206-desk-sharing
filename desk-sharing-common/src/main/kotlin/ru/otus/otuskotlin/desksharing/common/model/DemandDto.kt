package ru.otus.otuskotlin.desksharing.common.model

import NONE
import kotlinx.datetime.LocalDate

data class DemandDto (

    var date: LocalDate = LocalDate.NONE,
    var bookingDate: LocalDate = LocalDate.NONE,
    var employeeId: DskShrngId = DskShrngId.NONE,
    var status: DemandStatus = DemandStatus.NONE,
    var number: String = "",
    var workDeskNumber: WorkDeskNumber = WorkDeskNumber.NONE,
    var declineReason: String = "",
    var demandId: DskShrngId = DskShrngId.NONE,
    var userId: DemandUserId = DemandUserId.NONE,
    var lock: String = "",
    val permissions: MutableSet<DemandPermissionClient> = mutableSetOf()

){
    fun deepCopy(): DemandDto = copy(
        permissions = permissions.toMutableSet()
    )
}

