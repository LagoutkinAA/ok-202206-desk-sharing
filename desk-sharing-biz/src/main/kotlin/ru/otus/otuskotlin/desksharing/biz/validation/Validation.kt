package ru.otus.otuskotlin.desksharing.biz.validation

import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.chain


fun ICorChainDsl<DemandContext>.validation(block: ICorChainDsl<DemandContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == DemandState.RUNNING }
}
