package ru.otus.otuskotlin.desksharing.repository.inmemory.model

import kotlinx.datetime.LocalDate
import ru.otus.otuskotlin.desksharing.common.NONE
import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DemandStatus
import ru.otus.otuskotlin.desksharing.common.model.DemandUserId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.model.WorkDeskNumber

data class DemandEntity(
    val date: LocalDate? = null,
    val bookingDate: LocalDate? = null,
    val employeeId: String? = null,
    val status: String? = null,
    val number: String? = null,
    val workDeskNumber: String? = null,
    val declineReason: String? = null,
    val demandId: String? = null,
    val userId: String? = null,
    val lock: String? = null
) {
    constructor(model: DemandDto) : this(
        date = model.date.takeIf { it != LocalDate.NONE },
        bookingDate = model.bookingDate.takeIf { it != LocalDate.NONE },
        employeeId = model.employeeId.asString().takeIf { it.isNotBlank() },
        status = model.status.name.takeIf { it.isNotBlank() },
        number = model.number.takeIf { it.isNotBlank() },
        workDeskNumber = model.workDeskNumber.asString().takeIf { it.isNotBlank() },
        declineReason = model.declineReason.takeIf { it.isNotBlank() },
        demandId = model.demandId.asString().takeIf { it.isNotBlank() },
        userId = model.userId.asString().takeIf { it.isNotBlank() },
        lock = model.lock.takeIf { it.isNotBlank() }
    )

    fun toInternal() = DemandDto(
        date = date ?: LocalDate.NONE,
        bookingDate = bookingDate ?: LocalDate.NONE,
        employeeId = employeeId?.let { DskShrngId(it) } ?: DskShrngId.NONE,
        status = status?.let { DemandStatus.valueOf(it) } ?: DemandStatus.NONE,
        number = number ?: "",
        workDeskNumber = workDeskNumber?.let { WorkDeskNumber(it) } ?: WorkDeskNumber.NONE,
        declineReason = declineReason ?: "",
        demandId = demandId?.let { DskShrngId(it) } ?: DskShrngId.NONE,
        userId = userId?.let { DemandUserId(it) } ?: DemandUserId.NONE,
        lock = lock ?: ""
    )
}
