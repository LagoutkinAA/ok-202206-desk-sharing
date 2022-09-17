import kotlinx.datetime.LocalDate
import ru.otus.otuskotlin.deskSharing.api.v1.models.*
import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.*
import ru.otus.otuskotlin.desksharing.mappers.exceptions.UnknownDemandCommand

fun DemandContext.toTransportDemand(): IResponse = when (val cmd = command) {
    DemandCommand.CREATE -> toTransportCreate()
    DemandCommand.READ -> toTransportRead()
    DemandCommand.UPDATE -> toTransportUpdate()
    DemandCommand.DELETE -> toTransportDelete()
    DemandCommand.SEARCH -> toTransportSearch()
    DemandCommand.NONE -> throw UnknownDemandCommand(cmd)
}

fun DemandContext.toTransportCreate() = DemandCreateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == DemandState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    demand = demandResponse.toTransportDemand()
)

fun DemandContext.toTransportRead() = DemandReadResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == DemandState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    demand = demandResponse.toTransportDemand()
)

fun DemandContext.toTransportUpdate() = DemandUpdateResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == DemandState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    demand = demandResponse.toTransportDemand()
)

fun DemandContext.toTransportDelete() = DemandDeleteResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == DemandState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    demand = demandResponse.toTransportDemand()
)

fun DemandContext.toTransportSearch() = DemandSearchResponse(
    requestId = this.requestId.asString().takeIf { it.isNotBlank() },
    result = if (state == DemandState.RUNNING) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors(),
    demands = demandResponses.toTransportDemand()
)

fun List<DemandDto>.toTransportDemand(): List<DemandResponseObjectDto>? = this
    .map { it.toTransportDemand() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun DemandDto.toTransportDemand(): DemandResponseObjectDto = DemandResponseObjectDto(
    date = date.takeIf { it != LocalDate.NONE }?.toString(),
    bookingDate = bookingDate.takeIf { it != LocalDate.NONE }?.toString(),
    employeeId = employeeId.takeIf { it != DskShrngId.NONE }?.asString(),
    status = status.toTransport(),
    number = number.takeIf { it.isNotBlank() },
    workDeskNumber = workDeskNumber.takeIf { it != WorkDeskNumber.NONE }?.asString(),
    declineReason = declineReason.takeIf { it.isNotBlank() },
    demandId = demandId.takeIf { it != DskShrngId.NONE }?.asString(),
    userId = userId.takeIf { it != DemandUserId.NONE }?.asString(),
    lock = lock.takeIf { it.isNotBlank() },
    permissions = permissions.toTransportDemand()
)

private fun Set<DemandPermissionClient>.toTransportDemand(): Set<DemandPermissions>? = this
    .map { it.toTransportDemand() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun DemandPermissionClient.toTransportDemand() = when (this) {
    DemandPermissionClient.READ -> DemandPermissions.READ
    DemandPermissionClient.UPDATE -> DemandPermissions.UPDATE
    DemandPermissionClient.DELETE -> DemandPermissions.DELETE
}

private fun List<DemandError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportDemand() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun DemandError.toTransportDemand() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

private fun DemandStatus.toTransport(): DemandApiStatus? = when (this) {
    DemandStatus.NEW -> DemandApiStatus.NEW
    DemandStatus.DECLINED -> DemandApiStatus.DECLINED
    DemandStatus.DELETED -> DemandApiStatus.DELETED
    DemandStatus.ACCEPTED -> DemandApiStatus.ACCEPTED
    DemandStatus.ERROR -> DemandApiStatus.ERROR
    DemandStatus.CONFIRMED -> DemandApiStatus.CONFIRMED
    DemandStatus.NONE -> null
}
