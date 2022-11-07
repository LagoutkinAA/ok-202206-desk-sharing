package ru.otus.otuskotlin.desksharing.biz.stub

import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.stubs.DemandStubs
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.worker
import ru.otus.otuskotlin.desksharing.stub.DeskSharingDemandStub

fun ICorChainDsl<DemandContext>.stubSearchSuccess(title: String) = worker {
    this.title = title
    on { stubCase == DemandStubs.SUCCESS && state == DemandState.RUNNING }
    handle {
        state = DemandState.FINISHING
        demandsResponse.addAll(
            DeskSharingDemandStub.prepareSearchList(
                demandFilterRequest.employeeId,
                demandFilterRequest.dateFrom,
                demandFilterRequest.dateTo
            )
        )
    }
}
