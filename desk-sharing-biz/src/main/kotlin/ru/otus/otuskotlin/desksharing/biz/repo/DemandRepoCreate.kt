package ru.otus.otuskotlin.desksharing.biz.repo

import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandRequest
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.worker

fun ICorChainDsl<DemandContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление заявки в БД"
    on { state == DemandState.RUNNING }
    handle {
        val request = DbDemandRequest(demandRepoPrepare)
        val result = demandRepo.createDemand(request)
        val resultDemand = result.data
        if (result.isSuccess && resultDemand != null) {
            demandRepoDone = resultDemand
        } else {
            state = DemandState.FAILING
            errors.addAll(result.errors)
        }
    }
}
