package ru.otus.otuskotlin.desksharing.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import ru.otus.otuskotlin.desksharing.biz.DemandProcessor
import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandCommand
import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.model.DemandStatus
import ru.otus.otuskotlin.desksharing.common.model.DemandUserId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngWorkMode
import ru.otus.otuskotlin.desksharing.common.model.WorkDeskNumber
import ru.otus.otuskotlin.desksharing.common.now
import ru.otus.otuskotlin.desksharing.common.permission.DemandPrincipalModel
import ru.otus.otuskotlin.desksharing.common.permission.DemandUserGroups
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdCorrect(command: DemandCommand, processor: DemandProcessor) = runTest {
    val ctx = DemandContext(
        command = command,
        state = DemandState.NONE,
        workMode = DskShrngWorkMode.TEST,
        demandRequest = DemandDto(
            date = LocalDate.now(),
            bookingDate = LocalDate.now().plus(DatePeriod(days = 2)),
            employeeId = DskShrngId("123-234-abc-ABC"),
            status = DemandStatus.NEW,
            demandId = DskShrngId("123-234-abc-ABC"),
            workDeskNumber = WorkDeskNumber.NONE,
            number = "",
            userId = DemandUserId("123-234-abc-ABC"),
            lock = "123-234-abc-ABC"
        ),
        principal = DemandPrincipalModel(
            id = DemandUserId("123-234-abc-ABC"),
            groups = setOf(
                DemandUserGroups.USER,
                DemandUserGroups.TEST,
            )
        )
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(DemandState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdEmpty(command: DemandCommand, processor: DemandProcessor) = runTest {
    val ctx = DemandContext(
        command = command,
        state = DemandState.NONE,
        workMode = DskShrngWorkMode.TEST,
        demandRequest = DemandDto(
            date = LocalDate.now(),
            bookingDate = LocalDate.now().plus(DatePeriod(days = 2)),
            employeeId = DskShrngId("123-234-abc-ABC"),
            status = DemandStatus.NEW,
            demandId = DskShrngId(""),
            workDeskNumber = WorkDeskNumber.NONE,
            number = "",
            userId = DemandUserId("123-234-abc-ABC"),
            lock = "123-234-abc-ABC"
        ),
        principal = DemandPrincipalModel(
            id = DemandUserId("123-234-abc-ABC"),
            groups = setOf(
                DemandUserGroups.USER,
                DemandUserGroups.TEST,
            )
        )
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(DemandState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("demandId", error?.field)
    assertContains(error?.message ?: "", "demandId")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdFormat(command: DemandCommand, processor: DemandProcessor) = runTest {
    val ctx = DemandContext(
        command = command,
        state = DemandState.NONE,
        workMode = DskShrngWorkMode.TEST,
        demandRequest = DemandDto(
            date = LocalDate.now(),
            bookingDate = LocalDate.now().plus(DatePeriod(days = 2)),
            employeeId = DskShrngId("123-234-abc-ABC"),
            status = DemandStatus.NEW,
            demandId = DskShrngId("\"!@#\\\$%^&*(),.{}\""),
            workDeskNumber = WorkDeskNumber.NONE,
            number = "",
            userId = DemandUserId("123-234-abc-ABC"),
            lock = "123-234-abc-ABC"
        ),
        principal = DemandPrincipalModel(
            id = DemandUserId("123-234-abc-ABC"),
            groups = setOf(
                DemandUserGroups.USER,
                DemandUserGroups.TEST,
            )
        )
    )

    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(DemandState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("demandId", error?.field)
    assertContains(error?.message ?: "", "demandId")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationEmployeeIdCorrect(command: DemandCommand, processor: DemandProcessor) = runTest {
    val ctx = DemandContext(
        command = command,
        state = DemandState.NONE,
        workMode = DskShrngWorkMode.TEST,
        demandRequest = DemandDto(
            date = LocalDate.now(),
            bookingDate = LocalDate.now().plus(DatePeriod(days = 2)),
            employeeId = DskShrngId("123-234-abc-ABC"),
            status = DemandStatus.NEW,
            demandId = DskShrngId("123-234-abc-ABC"),
            workDeskNumber = WorkDeskNumber.NONE,
            number = "",
            userId = DemandUserId("123-234-abc-ABC"),
            lock = "123-234-abc-ABC"
        ),
        principal = DemandPrincipalModel(
            id = DemandUserId("123-234-abc-ABC"),
            groups = setOf(
                DemandUserGroups.USER,
                DemandUserGroups.TEST,
            )
        )
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(DemandState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationEmployeeIdEmpty(command: DemandCommand, processor: DemandProcessor) = runTest {
    val ctx = DemandContext(
        command = command,
        state = DemandState.NONE,
        workMode = DskShrngWorkMode.TEST,
        demandRequest = DemandDto(
            date = LocalDate.now(),
            bookingDate = LocalDate.now().plus(DatePeriod(days = 2)),
            employeeId = DskShrngId(""),
            status = DemandStatus.NEW,
            demandId = DskShrngId("123-234-abc-ABC"),
            workDeskNumber = WorkDeskNumber.NONE,
            number = "",
            userId = DemandUserId("123-234-abc-ABC"),
            lock = "123-234-abc-ABC"
        ),
        principal = DemandPrincipalModel(
            id = DemandUserId("123-234-abc-ABC"),
            groups = setOf(
                DemandUserGroups.USER,
                DemandUserGroups.TEST,
            )
        )
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(DemandState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("employeeId", error?.field)
    assertContains(error?.message ?: "", "employeeId")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationEmployeeIdFormat(command: DemandCommand, processor: DemandProcessor) = runTest {
    val ctx = DemandContext(
        command = command,
        state = DemandState.NONE,
        workMode = DskShrngWorkMode.TEST,
        demandRequest = DemandDto(
            date = LocalDate.now(),
            bookingDate = LocalDate.now().plus(DatePeriod(days = 2)),
            employeeId = DskShrngId("\"!@#\\\$%^&*(),.{}\""),
            status = DemandStatus.NEW,
            demandId = DskShrngId("123-234-abc-ABC"),
            workDeskNumber = WorkDeskNumber.NONE,
            number = "",
            userId = DemandUserId("123-234-abc-ABC"),
            lock = "123-234-abc-ABC"
        ),
        principal = DemandPrincipalModel(
            id = DemandUserId("123-234-abc-ABC"),
            groups = setOf(
                DemandUserGroups.USER,
                DemandUserGroups.TEST,
            )
        )
    )

    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(DemandState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("employeeId", error?.field)
    assertContains(error?.message ?: "", "employeeId")
}