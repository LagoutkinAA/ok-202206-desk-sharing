import exceptions.UnknownDemandCommand
import kotlinx.datetime.LocalDate
import model.*
import ru.otus.otuskotlin.deskSharing.api.v1.models.*
import ru.otus.otuskotlin.deskSharing.api.v1.models.DemandStatus

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

fun List<Demand>.toTransportDemand(): List<DemandResponseObjectDto>? = this
    .map { it.toTransportDemand() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Demand.toTransportDemand(): DemandResponseObjectDto = DemandResponseObjectDto(
    date = date.takeIf { it != LocalDate.NONE }?.toString(),
    bookingDate = bookingDate.takeIf { it != LocalDate.NONE }?.toString(),
    employeeId = employeeId.takeIf { it != DskShrngId.NONE }?.asString(),
    branchId = branchId.takeIf { it != DskShrngId.NONE }?.asString(),
    buildingId = buildingId.takeIf { it != DskShrngId.NONE }?.asString(),
    status = status.toTransport(),
    number = number.takeIf { it.isNotBlank() },
    workDeskId = workDeskId.takeIf { it != DskShrngId.NONE }?.asString(),
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

private fun model.DemandStatus.toTransport(): DemandStatus? = when (this) {
    model.DemandStatus.NEW -> DemandStatus.NEW
    model.DemandStatus.DECLINED -> DemandStatus.DECLINED
    model.DemandStatus.DELETED -> DemandStatus.DELETED
    model.DemandStatus.ACCEPTED -> DemandStatus.ACCEPTED
    model.DemandStatus.NONE -> null
}
