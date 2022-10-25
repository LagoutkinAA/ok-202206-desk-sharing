package ru.otus.otuskotlin.desksharing.repository.tests

import kotlinx.datetime.LocalDate
import now
import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DemandStatus
import ru.otus.otuskotlin.desksharing.common.model.DemandUserId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.model.WorkDeskNumber

abstract class BaseInitDemands(val op: String) : IInitObjects<DemandDto> {

    open val lockOld: String = "20000000-0000-0000-0000-000000000001"
    open val lockBad: String = "20000000-0000-0000-0000-000000000009"

    fun createInitTestModel(
        suf: String,
        demandId: DskShrngId = DskShrngId("ad-repo-$op-$suf"),
        employeeId: DskShrngId = DskShrngId("e777"),
        userId: DemandUserId = DemandUserId("u777"),
        date: LocalDate = LocalDate.now(),
        bookingDate: LocalDate = LocalDate.now(),
        workDeskNumber: WorkDeskNumber = WorkDeskNumber("1/1"),
        number: String = "1",
        lock: String = lockOld
    ) = DemandDto(
        date = date,
        bookingDate = bookingDate,
        employeeId = employeeId,
        status = DemandStatus.ACCEPTED,
        demandId = demandId,
        workDeskNumber = workDeskNumber,
        number = number,
        userId = userId,
        lock = lock
    )
}
