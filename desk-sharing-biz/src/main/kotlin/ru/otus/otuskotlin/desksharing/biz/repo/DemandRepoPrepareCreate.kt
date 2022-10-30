package ru.otus.otuskotlin.desksharing.biz.repo

import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.worker

fun ICorChainDsl<DemandContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка заявки к сохранению в базе данных"
    on { state == DemandState.RUNNING }
    handle {
        demandRepoPrepare = demandRequestValidated.deepCopy().apply {
            employeeId = DskShrngId("my-owner-id")
        }

    }
}
