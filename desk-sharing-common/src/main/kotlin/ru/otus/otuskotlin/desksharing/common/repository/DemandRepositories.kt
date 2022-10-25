package ru.otus.otuskotlin.desksharing.common.repository

data class DemandRepositories(
    val prod: IDemandRepository = IDemandRepository.NONE,
    val test: IDemandRepository = IDemandRepository.NONE,
) {
    companion object {
        val NONE = DemandRepositories()
    }
}
