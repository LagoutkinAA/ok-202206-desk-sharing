package ru.otus.otuskotlin.desksharing.biz.repo

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DateTimeUnit.Companion.DAY
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
import ru.otus.otuskotlin.desksharing.common.model.WorkDeskNumber
import ru.otus.otuskotlin.desksharing.common.now
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandResponse
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandsResponse
import ru.otus.otuskotlin.desksharing.repository.tests.DemandRepositoryMock
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoCreateTest {

    private val command = DemandCommand.CREATE
    private val uuid = "10000000-0000-0000-0000-000000000001"

    private val initDemand = DemandDto(
        date = LocalDate.now(),
        bookingDate = LocalDate.now().plus(1, DateTimeUnit.DAY),
        employeeId = DskShrngId("123"),
        status = DemandStatus.ACCEPTED,
        number = "1",
        userId = DemandUserId("123"),
        demandId = DskShrngId("123"),
        workDeskNumber = WorkDeskNumber("1"),
        lock = uuid
    )

    private val repo = DemandRepositoryMock(
        invokeCreateDemand = {
            DbDemandResponse(
                isSuccess = true,
                data = DemandDto(
                    date = it.demand.date,
                    bookingDate = it.demand.bookingDate,
                    employeeId = it.demand.employeeId,
                    status = it.demand.status,
                    number = it.demand.number,
                    demandId = DskShrngId(uuid)
                )
            )
        },
        invokeReadDemand = {
            DbDemandResponse(
                isSuccess = true,
                data = initDemand,
            )
        },
        invokeUpdateDemand = {
            DbDemandResponse(
                isSuccess = true,
                data = initDemand
            )
        },
        invokeSearchDemand = {
            DbDemandsResponse(
                isSuccess = true,
                data = listOf(initDemand),
            )
        }
    )
    private val settings = DemandSettings(
        repoTest = repo
    )
    private val processor = DemandProcessor(settings)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoCreateSuccessTest() = runTest {
        val ctx = DemandContext(
            command = command,
            state = DemandState.NONE,
            workMode = DskShrngWorkMode.TEST,
            demandRequest = DemandDto(
                date = LocalDate.now(),
                bookingDate = LocalDate.now().plus(1, DAY),
                employeeId = DskShrngId("123"),
                status = DemandStatus.NONE,
                number = "",
                userId = DemandUserId("123")
            ),
        )
        processor.exec(ctx)
        assertEquals(DemandState.FINISHING, ctx.state)
        assertNotEquals(DskShrngId.NONE, ctx.demandResponse.demandId)
    }
}
