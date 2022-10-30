package ru.otus.otuskotlin.desksharing.biz.repo

import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandFilterRequest
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.worker

fun ICorChainDsl<DemandContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск заявок в БД по фильтру"
    on { state == DemandState.RUNNING }
    handle {
        val request = DbDemandFilterRequest(
            employeeId = demandFilterRequestValidated.employeeId,
            dateFrom = demandFilterRequestValidated.dateFrom,
            dateTo = demandFilterRequestValidated.dateTo,
        )
        val result = demandRepo.searchDemand(request)
        val resultDemands = result.data
        if (result.isSuccess && resultDemands != null) {
            demandsRepoDone = resultDemands.toMutableList()
        } else {
            state = DemandState.FAILING
            errors.addAll(result.errors)
        }
    }
}
