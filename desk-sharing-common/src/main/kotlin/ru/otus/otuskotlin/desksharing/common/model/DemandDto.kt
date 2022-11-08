package ru.otus.otuskotlin.desksharing.common.model

import kotlinx.datetime.LocalDate
import ru.otus.otuskotlin.desksharing.common.NONE
import ru.otus.otuskotlin.desksharing.common.permission.DemandPermissionClient
import ru.otus.otuskotlin.desksharing.common.permission.DemandPrincipalRelations

data class DemandDto(

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
    var principalRelations: Set<DemandPrincipalRelations> = emptySet(),
    val permissionsClient: MutableSet<DemandPermissionClient> = mutableSetOf(),
) {
    fun deepCopy(): DemandDto = copy(
        principalRelations = principalRelations.toSet(),
        permissionsClient = permissionsClient.toMutableSet(),
    )

    companion object {
        val NONE get() = DemandDto()
    }
}

