package ru.otus.otuskotlin.desksharing.stub

import kotlinx.datetime.LocalDate
import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.stub.DeskSharingDemand.DEMAND

object DeskSharingDemandStub {
    fun get(): DemandDto = DEMAND

    fun prepareResult(block: DemandDto.() -> Unit): DemandDto = get().apply(block)

    fun prepareSearchList(employeeId: DskShrngId, demandDate: LocalDate, bookingDate: LocalDate) = listOf(
        deskSharingDemand("2e07327d-ffff-4da1-9c89-eff53a37c000", employeeId, demandDate, bookingDate),
        deskSharingDemand("2e07327d-ffff-4da1-9c89-eff53a37c001", employeeId, demandDate, bookingDate),
        deskSharingDemand("2e07327d-ffff-4da1-9c89-eff53a37c002", employeeId, demandDate, bookingDate)
    )

    private fun deskSharingDemand(id: String, employeeId: DskShrngId, demandDate: LocalDate, bookingDate: LocalDate) =
        deskSharingDemand(DEMAND, id = id, employeeId = employeeId, demandDate = demandDate, bookingDate = bookingDate)

    private fun deskSharingDemand(
        base: DemandDto,
        id: String,
        employeeId: DskShrngId,
        demandDate: LocalDate,
        bookingDate: LocalDate
    ) = base.copy(
        demandId = DskShrngId(id),
        employeeId = employeeId,
        date = demandDate,
        bookingDate = bookingDate
    )
}