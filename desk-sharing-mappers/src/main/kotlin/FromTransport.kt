import exceptions.UnknownRequestClass
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDate
import model.*
import ru.otus.otuskotlin.deskSharing.api.v1.models.*
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandStatus
import stubs.DemandStubs

fun DemandContext.fromTransport(request: IRequest) = when (request) {
    is DemandCreateRequest -> fromTransport(request)
    is DemandReadRequest -> fromTransport(request)
    is DemandUpdateRequest -> fromTransport(request)
    is DemandDeleteRequest -> fromTransport(request)
    is DemandSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toDskShrngId() = this?.let { DskShrngId(it) } ?: DskShrngId.NONE
private fun String?.toDemandWithId() = DemandDto(demandId = this.toDskShrngId())
private fun IRequest?.requestId() = this?.requestId?.let { DemandRequestId(it) } ?: DemandRequestId.NONE

private fun DemandStatus?.fromTransport(): model.DemandStatus = when (this) {
    DemandStatus.NEW -> model.DemandStatus.NEW
    DemandStatus.DECLINED -> model.DemandStatus.DECLINED
    DemandStatus.DELETED -> model.DemandStatus.DELETED
    DemandStatus.ACCEPTED -> model.DemandStatus.ACCEPTED
    null -> model.DemandStatus.NONE
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
    demandRequest = request.demand?.id.toDemandWithId()
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
    demandRequest = request.demand?.id.toDemandWithId()
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
    branchId = this.branchId.toDskShrngId(),
    buildingId = this.buildingId.toDskShrngId(),
    status = status.fromTransport()
)

private fun DemandUpdateObjectDto.toInternal(): DemandDto = DemandDto(
    date = toLocalDate(this.date),
    bookingDate = toLocalDate(this.bookingDate),
    employeeId = this.employeeId.toDskShrngId(),
    branchId = this.branchId.toDskShrngId(),
    buildingId = this.buildingId.toDskShrngId(),
    status = status.fromTransport(),
    number = this.number ?: "",
    workDeskId = this.workDeskId.toDskShrngId(),
    declineReason = this.declineReason ?: "",
    demandId = this.id.toDskShrngId(),
    lock = this.lock ?: ""
)

private fun toLocalDate(value: String?): LocalDate {
    return value?.toLocalDate() ?: LocalDate.NONE
}

