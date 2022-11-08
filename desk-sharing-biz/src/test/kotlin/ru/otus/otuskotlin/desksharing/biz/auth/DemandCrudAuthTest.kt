package ru.otus.otuskotlin.desksharing.biz.auth

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.desksharing.biz.DemandProcessor
import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandCommand
import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DemandSettings
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.model.DemandUserId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngWorkMode
import ru.otus.otuskotlin.desksharing.common.permission.DemandPermissionClient
import ru.otus.otuskotlin.desksharing.common.permission.DemandPrincipalModel
import ru.otus.otuskotlin.desksharing.common.permission.DemandUserGroups
import ru.otus.otuskotlin.desksharing.repository.inmemory.DemandRepoInMemory
import ru.otus.otuskotlin.desksharing.stub.DeskSharingDemandStub
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * @crud - экземпляр класса-фасада бизнес-логики
 * @context - контекст, смапленный из транспортной модели запроса
 */
@OptIn(ExperimentalCoroutinesApi::class)
class DemandCrudAuthTest {
    @Test
    fun createSuccessTest() = runTest {
        val userId = DemandUserId("123")
        val repo = DemandRepoInMemory()
        val processor = DemandProcessor(
            settings = DemandSettings(
                repoTest = repo
            )
        )
        val context = DemandContext(
            workMode = DskShrngWorkMode.TEST,
            demandRequest = DeskSharingDemandStub.prepareResult {
                permissionsClient.clear()
                demandId = DskShrngId.NONE
            },
            command = DemandCommand.CREATE,
            principal = DemandPrincipalModel(
                id = userId,
                groups = setOf(
                    DemandUserGroups.USER,
                    DemandUserGroups.TEST,
                )
            )
        )

        processor.exec(context)
        assertEquals(DemandState.FINISHING, context.state)
        with(context.demandResponse) {
            assertTrue { demandId.asString().isNotBlank() }
            assertContains(permissionsClient, DemandPermissionClient.READ)
            assertContains(permissionsClient, DemandPermissionClient.UPDATE)
            assertContains(permissionsClient, DemandPermissionClient.DELETE)
        }
    }

    @Test
    fun readSuccessTest() = runTest {
        val adObj = DeskSharingDemandStub.get()
        val userId = adObj.userId
        val adId = adObj.demandId
        val repo = DemandRepoInMemory(initObjects = listOf(adObj))
        val processor = DemandProcessor(
            settings = DemandSettings(
                repoTest = repo
            )
        )
        val context = DemandContext(
            command = DemandCommand.READ,
            workMode = DskShrngWorkMode.TEST,
            demandRequest = DemandDto(demandId = adId),
            principal = DemandPrincipalModel(
                id = userId,
                groups = setOf(
                    DemandUserGroups.USER,
                    DemandUserGroups.TEST,
                )
            )
        )
        processor.exec(context)
        assertEquals(DemandState.FINISHING, context.state)
        with(context.demandResponse) {
            assertTrue { demandId.asString().isNotBlank() }
            assertContains(permissionsClient, DemandPermissionClient.READ)
            assertContains(permissionsClient, DemandPermissionClient.UPDATE)
            assertContains(permissionsClient, DemandPermissionClient.DELETE)
        }
    }

}
