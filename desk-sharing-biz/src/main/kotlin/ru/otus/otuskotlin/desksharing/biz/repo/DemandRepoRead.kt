package ru.otus.otuskotlin.desksharing.biz.repo

import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandIdRequest
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.worker

fun ICorChainDsl<DemandContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение заявки из БД"
    on { state == DemandState.RUNNING }
    handle {
        val request = DbDemandIdRequest(demandRequestValidated)
        val result = demandRepo.readDemand(request)
        val resultDemand = result.data
        if (result.isSuccess && resultDemand != null) {
            demandRepoRead = resultDemand
        } else {
            state = DemandState.FAILING
            errors.addAll(result.errors)
        }
    }
}
