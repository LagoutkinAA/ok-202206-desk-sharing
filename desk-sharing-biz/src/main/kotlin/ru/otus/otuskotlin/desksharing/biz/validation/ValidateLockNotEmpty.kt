package ru.otus.otuskotlin.desksharing.biz.validation

import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.helpers.errorValidation
import ru.otus.otuskotlin.desksharing.common.helpers.fail
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.worker

fun ICorChainDsl<DemandContext>.validateLockNotEmpty(title: String) = worker {
    this.title = title
    on {
        demandRequestValidating.lock == "" || demandRequestValidating.lock.isEmpty()
    }
    handle {
        fail(
            errorValidation(
                field = "lock",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}