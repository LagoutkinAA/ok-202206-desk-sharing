package ru.otus.otuskotlin.desksharing.biz.permissions

import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.permission.DemandSearchPermissions
import ru.otus.otuskotlin.desksharing.common.permission.DemandUserPermissions
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.chain
import ru.otus.otuskotlin.desksharing.cor.worker

fun ICorChainDsl<DemandContext>.searchTypes(title: String) = chain {
    this.title = title
    description = "Добавление ограничений в поисковый запрос согласно правам доступа и др. политикам"
    on { state == DemandState.RUNNING }
    worker("Определение типа поиска") {
        demandFilterRequestValidated.searchPermissions = setOfNotNull(
            DemandSearchPermissions.OWN.takeIf { permissionsChain.contains(DemandUserPermissions.SEARCH_OWN) },
            DemandSearchPermissions.PUBLIC.takeIf { permissionsChain.contains(DemandUserPermissions.SEARCH_PUBLIC) },
            DemandSearchPermissions.REGISTERED.takeIf { permissionsChain.contains(DemandUserPermissions.SEARCH_REGISTERED) },
        ).toMutableSet()
    }
}
