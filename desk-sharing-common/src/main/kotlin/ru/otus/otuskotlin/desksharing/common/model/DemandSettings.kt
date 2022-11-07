package ru.otus.otuskotlin.desksharing.common.model

import ru.otus.otuskotlin.desksharing.common.repository.IDemandRepository

data class DemandSettings(
    val repoStub: IDemandRepository = IDemandRepository.NONE,
    val repoTest: IDemandRepository = IDemandRepository.NONE,
    val repoProd: IDemandRepository = IDemandRepository.NONE,
    val workDeskNumbers: Int = 10
)
