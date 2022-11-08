package ru.otus.otuskotlin.desksharing.biz.permissions

import ru.otus.otuskotlin.desksharing.auth.checkPermitted
import ru.otus.otuskotlin.desksharing.auth.resolveRelationsTo
import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.helpers.fail
import ru.otus.otuskotlin.desksharing.common.model.DemandError
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.chain
import ru.otus.otuskotlin.desksharing.cor.worker

fun ICorChainDsl<DemandContext>.accessValidation(title: String) = chain {
    this.title = title
    description = "Вычисление прав доступа по группе принципала и таблице прав доступа"
    on { state == DemandState.RUNNING }
    worker("Вычисление отношения заявки к принципалу") {
        demandRepoRead.principalRelations = demandRepoRead.resolveRelationsTo(principal)
    }
    worker("Вычисление доступа к заявке") {
        permitted = checkPermitted(command, demandRepoRead.principalRelations, permissionsChain)
    }
    worker {
        this.title = "Валидация прав доступа"
        description = "Проверка наличия прав для выполнения операции"
        on { !permitted }
        handle {
            fail(DemandError(message = "User is not allowed to perform this operation"))
        }
    }
}

