import kotlinx.datetime.Instant
import model.*
import stubs.DemandStubs

data class DemandContext(
    var command: DemandCommand = DemandCommand.NONE,
    var state: DemandState = DemandState.NONE,
    val errors: MutableList<DemandError> = mutableListOf(),

    var workMode: DskShrngWorkMode = DskShrngWorkMode.PROD,
    var stubCase: DemandStubs = DemandStubs.NONE,

    var requestId: DemandRequestId = DemandRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var demandRequest: Demand = Demand(),
    var demandFilterRequest: DemandFilter = DemandFilter(),
    var demandResponse: Demand = Demand(),
    var demandsResponse: MutableList<Demand> = mutableListOf()
)