package ru.otus.otuskotlin.desksharing.biz.repo

import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.model.DemandStatus
import ru.otus.otuskotlin.desksharing.common.model.WorkDeskNumber
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandFilterRequest
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandRequest
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.worker
import java.util.stream.Collectors
import java.util.stream.IntStream

fun ICorChainDsl<DemandContext>.demandAssignNumber(title: String) = worker {
    this.title = title
    description = "Поиск свободного рабочего места"
    on { state == DemandState.RUNNING }
    handle {
        val demand = demandRepoDone.deepCopy()
        val searchRequest = DbDemandFilterRequest(
            dateFrom = demand.bookingDate,
            dateTo = demand.bookingDate,
        )
        val searchResult = demandRepo.searchDemand(searchRequest)
        val resultDemands = searchResult.data
        if (searchResult.isSuccess && resultDemands != null) {
            val numbers = resultDemands.stream()
                .filter {
                    (it.status == DemandStatus.ACCEPTED || it.status == DemandStatus.CONFIRMED)
                            && it.demandId != demand.demandId
                            && it.workDeskNumber != WorkDeskNumber.NONE
                }
                .map { it.workDeskNumber.asInt() }
                .collect(Collectors.toSet())

            if (numbers.isEmpty()) {
                demand.workDeskNumber = WorkDeskNumber("1")
                demand.status = DemandStatus.ACCEPTED
            } else {
                val freeNumber = IntStream.range(1, settings.workDeskNumbers)
                    .filter { !numbers.contains(it) }
                    .findFirst()
                    .orElse(-1)
                if (freeNumber == -1) {
                    demand.declineReason = "No free workdesk"
                    demand.status = DemandStatus.DECLINED
                } else {
                    demand.workDeskNumber = WorkDeskNumber(freeNumber.toString())
                    demand.status = DemandStatus.ACCEPTED
                }
            }
            val updateRequest = DbDemandRequest(
                demand.deepCopy()
            )
            val updateResult = demandRepo.updateDemand(updateRequest)
            val resultDemand = updateResult.data
            if (updateResult.isSuccess && resultDemand != null) {
                demandRepoDone = resultDemand
            } else {
                state = DemandState.FAILING
                errors.addAll(updateResult.errors)
                demandRepoDone
            }
        } else {
            state = DemandState.FAILING
            errors.addAll(searchResult.errors)
        }
    }
}
