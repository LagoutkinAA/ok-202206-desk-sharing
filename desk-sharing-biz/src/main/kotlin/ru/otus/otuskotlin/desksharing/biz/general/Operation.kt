package ru.otus.otuskotlin.desksharing.biz.general

import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandCommand
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.chain

fun ICorChainDsl<DemandContext>.operation(
    title: String,
    command: DemandCommand,
    block: ICorChainDsl<DemandContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == DemandState.RUNNING }
}
