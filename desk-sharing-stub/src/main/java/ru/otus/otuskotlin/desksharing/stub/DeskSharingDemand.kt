package ru.otus.otuskotlin.desksharing.stub

import kotlinx.datetime.LocalDate
import model.*

object DeskSharingDemand {
    val DEMAND: DemandDto
        get() = DemandDto(
            date = LocalDate(2022, 1, 1),
            bookingDate = LocalDate(2022, 1, 10),
            employeeId = DskShrngId("2e07327d-47e7-4da1-9c89-eff53a37c000"),
            status = DemandStatus.NEW,
            demandId = DskShrngId("2e07327d-ffff-4da1-9c89-eff53a37c000"),
            workDeskNumber = WorkDeskNumber("01a/1"),
            number = "01/001",
            permissions = mutableSetOf(
                DemandPermissionClient.READ,
                DemandPermissionClient.UPDATE,
                DemandPermissionClient.DELETE
            )
        )
}
