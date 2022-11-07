package ru.otus.otuskotlin.desksharing.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import ru.otus.otuskotlin.desksharing.biz.DemandProcessor
import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandCommand
import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DemandSettings
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.model.DemandStatus
import ru.otus.otuskotlin.desksharing.common.model.DemandUserId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngWorkMode
import ru.otus.otuskotlin.desksharing.common.now
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandResponse
import ru.otus.otuskotlin.desksharing.repository.tests.DemandRepositoryMock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoDeleteTest {

    private val command = DemandCommand.DELETE
    private val uuidOld = "10000000-0000-0000-0000-000000000001"
    private val uuidNew = "10000000-0000-0000-0000-000000000002"
    private val uuidBad = "10000000-0000-0000-0000-000000000003"
    private val initDemand = DemandDto(
        date = LocalDate.now(),
        bookingDate = LocalDate.now().plus(1, DateTimeUnit.DAY),
        employeeId = DskShrngId("123"),
        status = DemandStatus.NEW,
        number = "1",
        userId = DemandUserId("123"),
        demandId = DskShrngId("123"),
        lock = uuidOld
    )

    private val repo by lazy {
        DemandRepositoryMock(
            invokeReadDemand = {
                DbDemandResponse(
                    isSuccess = true,
                    data = initDemand,
                )
            },
            invokeDeleteDemand = {
                if (it.id == initDemand.demandId)
                    DbDemandResponse(
                        isSuccess = true,
                        data = initDemand
                    )
                else DbDemandResponse(isSuccess = false, data = null)
            }
        )
    }
    private val settings by lazy {
        DemandSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { DemandProcessor(settings) }

    @Test
    fun repoDeleteSuccessTest() = runTest {
        val demandToUpdate = DemandDto(
            demandId = DskShrngId("123"),
            lock = uuidOld
        )
        val ctx = DemandContext(
            command = command,
            state = DemandState.NONE,
            workMode = DskShrngWorkMode.TEST,
            demandRequest = demandToUpdate,
        )
        processor.exec(ctx)
        assertEquals(DemandState.FINISHING, ctx.state)
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initDemand.demandId, ctx.demandResponse.demandId)
    }

    @Test
    fun repoDeleteNotFoundTest() = repoNotFoundTest(command)
}
