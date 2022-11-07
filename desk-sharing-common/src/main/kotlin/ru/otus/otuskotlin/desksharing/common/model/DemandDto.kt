package ru.otus.otuskotlin.desksharing.common.model

import kotlinx.datetime.LocalDate
import ru.otus.otuskotlin.desksharing.common.NONE

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

    companion object {
        val NONE get() = DemandDto()
    }
}

