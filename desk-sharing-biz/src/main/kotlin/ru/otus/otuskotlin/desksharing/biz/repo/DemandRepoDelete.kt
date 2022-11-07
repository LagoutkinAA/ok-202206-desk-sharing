package ru.otus.otuskotlin.desksharing.biz.repo

import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.model.DemandStatus
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandIdRequest
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.worker

fun ICorChainDsl<DemandContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление заявления из БД по ID"
    on { state == DemandState.RUNNING }
    handle {
        val request = DbDemandIdRequest(demandRepoPrepare)
        val result = demandRepo.deleteDemand(request)
        if (!result.isSuccess) {
            state = DemandState.FAILING
            errors.addAll(result.errors)
        }
        demandRepoRead.status = DemandStatus.DELETED
        demandRepoDone = demandRepoRead
    }
}
