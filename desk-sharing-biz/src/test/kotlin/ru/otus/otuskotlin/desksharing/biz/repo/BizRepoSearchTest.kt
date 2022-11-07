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
import ru.otus.otuskotlin.desksharing.common.model.DemandFilter
import ru.otus.otuskotlin.desksharing.common.model.DemandSettings
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.model.DemandStatus
import ru.otus.otuskotlin.desksharing.common.model.DemandUserId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngWorkMode
import ru.otus.otuskotlin.desksharing.common.model.WorkDeskNumber
import ru.otus.otuskotlin.desksharing.common.now
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandsResponse
import ru.otus.otuskotlin.desksharing.repository.tests.DemandRepositoryMock
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoSearchTest {

    private val command = DemandCommand.SEARCH
    private val initDemand = DemandDto(
        date = LocalDate.now(),
        bookingDate = LocalDate.now().plus(1, DateTimeUnit.DAY),
        employeeId = DskShrngId("123"),
        status = DemandStatus.ACCEPTED,
        number = "1",
        userId = DemandUserId("123"),
        demandId = DskShrngId("123"),
        workDeskNumber = WorkDeskNumber("1")
    )
    private val repo by lazy {
        DemandRepositoryMock(
            invokeSearchDemand = {
                DbDemandsResponse(
                    isSuccess = true,
                    data = listOf(initDemand),
                )
            }
        )
    }
    private val settings by lazy {
        DemandSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { DemandProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoSearchSuccessTest() = runTest {
        val ctx = DemandContext(
            command = command,
            state = DemandState.NONE,
            workMode = DskShrngWorkMode.TEST,
            demandFilterRequest = DemandFilter(
                employeeId = DskShrngId("123"),
                dateFrom = LocalDate.now(),
                dateTo = LocalDate.now()
            ),
        )
        processor.exec(ctx)
        assertEquals(DemandState.FINISHING, ctx.state)
        assertEquals(1, ctx.demandsResponse.size)
    }
}
