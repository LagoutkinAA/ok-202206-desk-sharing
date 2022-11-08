package ru.otus.otuskotlin.desksharing.biz.permissions

import ru.otus.otuskotlin.desksharing.auth.resolveFrontPermissions
import ru.otus.otuskotlin.desksharing.auth.resolveRelationsTo
import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.worker

fun ICorChainDsl<DemandContext>.frontPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление разрешений пользователей для фронтенда"

    on { state == DemandState.RUNNING }

    handle {
        demandRepoDone.permissionsClient.addAll(
            resolveFrontPermissions(
                permissionsChain,
                // Повторно вычисляем отношения, поскольку они могли измениться при выполении операции
                demandRepoDone.resolveRelationsTo(principal)
            )
        )

        for (ad in demandsRepoDone) {
            ad.permissionsClient.addAll(
                resolveFrontPermissions(
                    permissionsChain,
                    ad.resolveRelationsTo(principal)
                )
            )
        }
    }
}
