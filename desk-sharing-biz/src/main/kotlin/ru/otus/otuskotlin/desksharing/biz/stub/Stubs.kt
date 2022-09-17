package ru.otus.otuskotlin.desksharing.biz.stub

import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.model.DskShrngWorkMode
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.chain

fun ICorChainDsl<DemandContext>.stubs(title: String, block: ICorChainDsl<DemandContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == DskShrngWorkMode.STUB && state == DemandState.RUNNING }
}
