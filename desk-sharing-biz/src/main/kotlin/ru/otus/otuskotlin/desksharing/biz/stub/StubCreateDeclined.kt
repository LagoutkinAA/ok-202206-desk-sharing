package ru.otus.otuskotlin.desksharing.biz.stub

import NONE
import kotlinx.datetime.LocalDate
import now
import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.*
import ru.otus.otuskotlin.desksharing.common.stubs.DemandStubs
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.worker
import ru.otus.otuskotlin.desksharing.stub.DeskSharingDemandStub

fun ICorChainDsl<DemandContext>.stubCreateDeclined(title: String) = worker {
    this.title = title
    on { stubCase == DemandStubs.NO_FREE_WORK_DESK && state == DemandState.RUNNING }
    handle {
        state = DemandState.FINISHING
        val stub = DeskSharingDemandStub.prepareResult {
            demandRequest.bookingDate.takeIf { it != LocalDate.NONE }?.also { this.bookingDate = it }
            demandRequest.employeeId.takeIf { it != DskShrngId.NONE }?.also { this.employeeId = it }
            demandRequest.demandId.takeIf { it != DskShrngId.NONE }?.also { this.demandId = it }
            demandRequest.userId.takeIf { it != DemandUserId.NONE }?.also { this.userId = it }
            this.status = DemandStatus.DECLINED
            this.date = LocalDate.now()
            this.number = "1"
            this.workDeskNumber = WorkDeskNumber.NONE
            this.declineReason = "No free workdesk"
        }
        demandResponse = stub
    }
}
