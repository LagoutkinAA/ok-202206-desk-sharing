package ru.otus.otuskotlin.desksharing.biz.general

import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.helpers.errorAdministration
import ru.otus.otuskotlin.desksharing.common.helpers.fail
import ru.otus.otuskotlin.desksharing.common.model.DskShrngWorkMode
import ru.otus.otuskotlin.desksharing.common.permission.DemandUserGroups
import ru.otus.otuskotlin.desksharing.common.repository.IDemandRepository
import ru.otus.otuskotlin.desksharing.cor.ICorChainDsl
import ru.otus.otuskotlin.desksharing.cor.worker

fun ICorChainDsl<DemandContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от запрошенного режима работы        
    """.trimIndent()
    handle {
        demandRepo = when {
            workMode == DskShrngWorkMode.TEST -> settings.repoTest
            workMode == DskShrngWorkMode.STUB -> settings.repoStub
            principal.groups.contains(DemandUserGroups.TEST) -> settings.repoTest
            else -> settings.repoProd
        }
        if (workMode != DskShrngWorkMode.STUB && demandRepo == IDemandRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}
