package ru.otus.otuskotlin.desksharing.biz.general

import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.model.DskShrngWorkMode
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.worker

fun ICorChainDsl<DemandContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != DskShrngWorkMode.STUB }
    handle {
        demandResponse = demandRepoDone
        demandsResponse = demandsRepoDone
        state = when (val st = state) {
            DemandState.RUNNING -> DemandState.FINISHING
            else -> st
        }
    }
}
