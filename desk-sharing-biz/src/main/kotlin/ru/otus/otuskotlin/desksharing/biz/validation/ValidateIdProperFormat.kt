package ru.otus.otuskotlin.desksharing.biz.validation

import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.helpers.errorValidation
import ru.otus.otuskotlin.desksharing.common.helpers.fail
import ru.otus.otuskotlin.desksharing.common.model.DemandUserId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.worker

fun ICorChainDsl<DemandContext>.validateEmployeeIdProperFormat(title: String) = worker {
    this.title = title

    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on {
        demandRequestValidating.employeeId != DskShrngId.NONE && !demandRequestValidating.employeeId.asString()
            .matches(regExp)
    }
    handle {
        val encodedId = demandRequestValidating.employeeId.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "employeeId",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}

fun ICorChainDsl<DemandContext>.validateUserIdProperFormat(title: String) = worker {
    this.title = title

    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on {
        demandRequestValidating.userId != DemandUserId.NONE && !demandRequestValidating.userId.asString()
            .matches(regExp)
    }
    handle {
        val encodedId = demandRequestValidating.userId.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "userId",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}

fun ICorChainDsl<DemandContext>.validateDemandIdProperFormat(title: String) = worker {
    this.title = title

    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on {
        demandRequestValidating.demandId != DskShrngId.NONE && !demandRequestValidating.demandId.asString()
            .matches(regExp)
    }
    handle {
        val encodedId = demandRequestValidating.demandId.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "demandId",
                violationCode = "badFormat",
                description = "value $encodedId must contain only"
            )
        )
    }
}
