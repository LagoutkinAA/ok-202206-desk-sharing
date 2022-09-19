package ru.otus.otuskotlin.desksharing.biz.validation

import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.worker

fun ICorChainDsl<DemandContext>.finishDemandValidation(title: String) = worker {
    this.title = title
    on { state == DemandState.RUNNING }
    handle {
        demandRequestValidated = demandRequestValidating
    }
}

fun ICorChainDsl<DemandContext>.finishDemandFilterValidation(title: String) = worker {
    this.title = title
    on { state == DemandState.RUNNING }
    handle {
        demandFilterRequestValidated = demandFilterRequestValidating
    }
}
