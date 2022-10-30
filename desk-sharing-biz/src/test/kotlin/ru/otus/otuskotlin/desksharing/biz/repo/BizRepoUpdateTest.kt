package ru.otus.otuskotlin.desksharing.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import now
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
import ru.otus.otuskotlin.desksharing.common.model.WorkDeskNumber
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandResponse
import ru.otus.otuskotlin.desksharing.repository.tests.DemandRepositoryMock
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoUpdateTest {

    private val command = DemandCommand.UPDATE
    private val uuidOld = "10000000-0000-0000-0000-000000000001"
    private val uuidNew = "10000000-0000-0000-0000-000000000002"
    private val uuidBad = "10000000-0000-0000-0000-000000000003"
    private val initDemand = DemandDto(
        date = LocalDate.now(),
        bookingDate = LocalDate.now().plus(1, DateTimeUnit.DAY),
        employeeId = DskShrngId("123"),
        status = DemandStatus.ACCEPTED,
        number = "1",
        userId = DemandUserId("123"),
        demandId = DskShrngId("123"),
        workDeskNumber = WorkDeskNumber("1"),
        lock = uuidOld
    )
    private val repo by lazy { DemandRepositoryMock(
        invokeReadDemand = {
            DbDemandResponse(
                isSuccess = true,
                data = initDemand,
            )
        },
        invokeUpdateDemand = {
            DbDemandResponse(
                isSuccess = true,
                data = DemandDto(
                    date = LocalDate.now(),
                    bookingDate = LocalDate.now().plus(1, DateTimeUnit.DAY),
                    employeeId = DskShrngId("123"),
                    status = DemandStatus.ACCEPTED,
                    number = "1",
                    userId = DemandUserId("123"),
                    demandId = DskShrngId("123"),
                    workDeskNumber = WorkDeskNumber("2"),
                )
            )
        }
    ) }
    private val settings by lazy {
        DemandSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { DemandProcessor(settings) }

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val demandToUpdate = DemandDto(
            date = LocalDate.now(),
            bookingDate = LocalDate.now().plus(1, DateTimeUnit.DAY),
            employeeId = DskShrngId("123"),
            status = DemandStatus.ACCEPTED,
            number = "1",
            userId = DemandUserId("123"),
            demandId = DskShrngId("123"),
            workDeskNumber = WorkDeskNumber("1"),
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
        assertEquals(demandToUpdate.demandId, ctx.demandResponse.demandId)
        assertEquals(demandToUpdate.workDeskNumber, ctx.demandResponse.workDeskNumber)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}
