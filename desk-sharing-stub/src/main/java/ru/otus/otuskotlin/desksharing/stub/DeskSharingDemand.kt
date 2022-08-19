package ru.otus.otuskotlin.desksharing.stub

import kotlinx.datetime.LocalDate
import model.DemandDto
import model.DemandPermissionClient
import model.DemandStatus
import model.DskShrngId

object DeskSharingDemand {
    val DEMAND: DemandDto
        get() = DemandDto(
            date = LocalDate(2022, 1, 1),
            bookingDate = LocalDate(2022, 1, 10),
            branchId = DskShrngId("2e07327d-47e7-4da1-9c89-eff53a37c111"),
            buildingId = DskShrngId("2e07327d-47e7-4da1-9c89-eff53a37c222"),
            employeeId = DskShrngId("2e07327d-47e7-4da1-9c89-eff53a37c000"),
            status = DemandStatus.NEW,
            demandId = DskShrngId("2e07327d-ffff-4da1-9c89-eff53a37c000"),
            workDeskId = DskShrngId("2e07327d-47e7-4da1-9c89-eff53a37caaa"),
            number = "01/001",
            permissions = mutableSetOf(
                DemandPermissionClient.READ,
                DemandPermissionClient.UPDATE,
                DemandPermissionClient.DELETE
            )
        )
}
