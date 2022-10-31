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
import ru.otus.otuskotlin.desksharing.common.model.DemandError
import ru.otus.otuskotlin.desksharing.common.model.DemandSettings
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.model.DemandStatus
import ru.otus.otuskotlin.desksharing.common.model.DemandUserId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngWorkMode
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandResponse
import ru.otus.otuskotlin.desksharing.common.repository.IDemandRepository
import ru.otus.otuskotlin.desksharing.repository.tests.DemandRepositoryMock
import kotlin.test.assertEquals

private val initDemand = DemandDto(
    date = LocalDate.now(),
    bookingDate = LocalDate.now().plus(1, DateTimeUnit.DAY),
    employeeId = DskShrngId("123"),
    status = DemandStatus.NONE,
    number = "",
    userId = DemandUserId("123")
)
private const val uuid = "10000000-0000-0000-0000-000000000001"
private val repo: IDemandRepository
    get() = DemandRepositoryMock(
        invokeReadDemand = {
            if (it.id == initDemand.demandId) {
                DbDemandResponse(
                    isSuccess = true,
                    data = initDemand,
                )
            } else DbDemandResponse(
                isSuccess = false,
                data = null,
                errors = listOf(DemandError(message = "Not found", field = "id"))
            )
        }
    )
private val settings by lazy {
    DemandSettings(
        repoTest = repo
    )
}
private val processor by lazy { DemandProcessor(settings) }

@OptIn(ExperimentalCoroutinesApi::class)
fun repoNotFoundTest(command: DemandCommand) = runTest {
    val ctx = DemandContext(
        command = command,
        state = DemandState.NONE,
        workMode = DskShrngWorkMode.TEST,
        demandRequest = DemandDto(
            date = LocalDate.now(),
            bookingDate = LocalDate.now().plus(1, DateTimeUnit.DAY),
            employeeId = DskShrngId("123"),
            status = DemandStatus.NONE,
            number = "",
            userId = DemandUserId("123"),
            lock = uuid
        ),
    )
    processor.exec(ctx)
    assertEquals(DemandState.FAILING, ctx.state)
    assertEquals(DemandDto(), ctx.demandResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("demandId", ctx.errors.first().field)
}
