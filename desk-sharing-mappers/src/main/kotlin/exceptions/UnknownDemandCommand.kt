package exceptions

import model.DemandCommand

class UnknownDemandCommand(command: DemandCommand) : Throwable("Wrong command $command at mapping toTransport stage")
