package ru.otus.otuskotlin.desksharing.mappers.exceptions

import ru.otus.otuskotlin.desksharing.common.model.DemandCommand

class UnknownDemandCommand(command: DemandCommand) : Throwable("Wrong command $command at mapping toTransport stage")
