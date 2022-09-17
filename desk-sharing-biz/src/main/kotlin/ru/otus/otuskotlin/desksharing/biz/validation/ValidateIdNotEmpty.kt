package ru.otus.otuskotlin.desksharing.biz.validation

import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.helpers.errorValidation
import ru.otus.otuskotlin.desksharing.common.helpers.fail
import ru.otus.otuskotlin.desksharing.common.model.DemandUserId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.worker

fun ICorChainDsl<DemandContext>.validateEmployeeIdNotEmpty(title: String) = worker {
    this.title = title
    on { demandRequestValidating.employeeId == DskShrngId.NONE }
    handle {
        fail(
            errorValidation(
                field = "employeeId",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}

fun ICorChainDsl<DemandContext>.validateUserIdNotEmpty(title: String) = worker {
    this.title = title
    on { demandRequestValidating.userId == DemandUserId.NONE }
    handle {
        fail(
            errorValidation(
                field = "userId",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}

fun ICorChainDsl<DemandContext>.validateDemandIdNotEmpty(title: String) = worker {
    this.title = title
    on { demandRequestValidating.demandId == DskShrngId.NONE }
    handle {
        fail(
            errorValidation(
                field = "demandId",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
