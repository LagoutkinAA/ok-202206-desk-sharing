import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandApiStatus
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandCreateObjectDto
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandCreateRequest
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandDebug
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandDeleteRequest
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandReadRequest
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandRequestDebugMode
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandRequestDebugStubs
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandSearchFilter
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandSearchRequest
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandUpdateObjectDto
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandUpdateRequest
import ru.otus.otuskotlin.deskSharing.api.v1.models.IRequest
import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.NONE
import ru.otus.otuskotlin.desksharing.common.model.DemandCommand
import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DemandFilter
import ru.otus.otuskotlin.desksharing.common.model.DemandRequestId
import ru.otus.otuskotlin.desksharing.common.model.DemandStatus
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngWorkMode
import ru.otus.otuskotlin.desksharing.common.model.WorkDeskNumber
import ru.otus.otuskotlin.desksharing.common.stubs.DemandStubs
import ru.otus.otuskotlin.desksharing.mappers.exceptions.UnknownRequestClass

fun DemandContext.fromTransport(request: IRequest) = when (request) {
    is DemandCreateRequest -> fromTransport(request)
    is DemandReadRequest -> fromTransport(request)
    is DemandUpdateRequest -> fromTransport(request)
    is DemandDeleteRequest -> fromTransport(request)
    is DemandSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toDskShrngId() = this?.let { DskShrngId(it) } ?: DskShrngId.NONE
private fun String?.toWorkDeskNumber() = this?.let { WorkDeskNumber(it) } ?: WorkDeskNumber.NONE
private fun String?.toDemandWithId() = DemandDto(demandId = this.toDskShrngId())
private fun IRequest?.requestId() = this?.requestId?.let { DemandRequestId(it) } ?: DemandRequestId.NONE

private fun DemandApiStatus?.fromTransport(): DemandStatus = when (this) {
    DemandApiStatus.NEW -> DemandStatus.NEW
    DemandApiStatus.DECLINED -> DemandStatus.DECLINED
    DemandApiStatus.DELETED -> DemandStatus.DELETED
    DemandApiStatus.ACCEPTED -> DemandStatus.ACCEPTED
    DemandApiStatus.ERROR -> DemandStatus.ERROR
    DemandApiStatus.CONFIRMED -> DemandStatus.CONFIRMED
    null -> DemandStatus.NONE
}

private fun DemandDebug?.transportToWorkMode(): DskShrngWorkMode = when (this?.mode) {
    DemandRequestDebugMode.PROD -> DskShrngWorkMode.PROD
    DemandRequestDebugMode.TEST -> DskShrngWorkMode.TEST
    DemandRequestDebugMode.STUB -> DskShrngWorkMode.STUB
    null -> DskShrngWorkMode.PROD
}

private fun DemandDebug?.transportToStubCase(): DemandStubs = when (this?.stub) {
    DemandRequestDebugStubs.SUCCESS -> DemandStubs.SUCCESS
    DemandRequestDebugStubs.NOT_FOUND -> DemandStubs.NOT_FOUND
    DemandRequestDebugStubs.BAD_ID -> DemandStubs.BAD_ID
    DemandRequestDebugStubs.CANNOT_DELETE -> DemandStubs.CANNOT_DELETE
    DemandRequestDebugStubs.VALIDATION_ERROR -> DemandStubs.VALIDATION_ERROR
    DemandRequestDebugStubs.NO_FREE_WORK_DESK -> DemandStubs.NO_FREE_WORK_DESK
    DemandRequestDebugStubs.DB_ERROR -> DemandStubs.DB_ERROR
    null -> DemandStubs.NONE
}

fun DemandContext.fromTransport(request: DemandCreateRequest) {
    command = DemandCommand.CREATE
    requestId = request.requestId()
    demandRequest = request.demand?.toInternal() ?: DemandDto()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun DemandContext.fromTransport(request: DemandReadRequest) {
    command = DemandCommand.READ
    requestId = request.requestId()
    demandRequest = request.demand?.demandId.toDemandWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun DemandContext.fromTransport(request: DemandUpdateRequest) {
    command = DemandCommand.UPDATE
    requestId = request.requestId()
    demandRequest = request.demand?.toInternal() ?: DemandDto()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun DemandContext.fromTransport(request: DemandDeleteRequest) {
    command = DemandCommand.DELETE
    requestId = request.requestId()
    demandRequest = request.demand?.demandId.toDemandWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun DemandContext.fromTransport(request: DemandSearchRequest) {
    command = DemandCommand.SEARCH
    requestId = request.requestId()
    demandFilterRequest = request.demandFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun DemandSearchFilter?.toInternal(): DemandFilter = DemandFilter(
    dateFrom = toLocalDate(this?.dateFrom),
    dateTo = toLocalDate(this?.dateTo),
    employeeId = this?.employeeId.toDskShrngId()
)

private fun DemandCreateObjectDto.toInternal(): DemandDto = DemandDto(
    date = toLocalDate(this.date),
    bookingDate = toLocalDate(this.bookingDate),
    employeeId = this.employeeId.toDskShrngId(),
    status = status.fromTransport()
)

private fun DemandUpdateObjectDto.toInternal(): DemandDto = DemandDto(
    date = toLocalDate(this.date),
    bookingDate = toLocalDate(this.bookingDate),
    employeeId = this.employeeId.toDskShrngId(),
    status = status.fromTransport(),
    number = this.number ?: "",
    workDeskNumber = this.workDeskNumber.toWorkDeskNumber(),
    declineReason = this.declineReason ?: "",
    demandId = this.demandId.toDskShrngId(),
    lock = this.lock ?: ""
)

private fun toLocalDate(value: String?): LocalDate {
    return value?.toLocalDate() ?: LocalDate.NONE
}

