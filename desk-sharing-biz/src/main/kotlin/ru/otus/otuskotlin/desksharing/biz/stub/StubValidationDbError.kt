package ru.otus.otuskotlin.desksharing.biz.stub

import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandError
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.stubs.DemandStubs
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.worker

fun ICorChainDsl<DemandContext>.stubDbError(title: String) = worker {
    this.title = title
    on { stubCase == DemandStubs.DB_ERROR && state == DemandState.RUNNING }
    handle {
        state = DemandState.FAILING
        this.errors.add(
            DemandError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}
