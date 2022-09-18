package ru.otus.otuskotlin.desksharing.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.desksharing.biz.DemandProcessor
import ru.otus.otuskotlin.desksharing.common.model.DemandCommand
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationUpdateTest {

    private val command = DemandCommand.UPDATE
    private val processor by lazy { DemandProcessor() }

    @Test fun correctBookingDate() = validationBookingDateCorrect(command, processor)
    @Test fun bookingDateIsEmpty() = validationBookingDateEmpty(command, processor)
    @Test fun bookingDateNotInRange() = validationBookingDateNotInRange(command, processor)

    @Test fun correctEmployeeId() = validationEmployeeIdCorrect(command, processor)
    @Test fun employeeIdIsEmpty() = validationEmployeeIdEmpty(command, processor)
    @Test fun employeeIdBadFormat() = validationEmployeeIdFormat(command, processor)

    @Test fun correctUserId() = validationUserIdCorrect(command, processor)
    @Test fun userIdIsEmpty() = validationUserIdEmpty(command, processor)
    @Test fun userIdBadFormat() = validationUserIdFormat(command, processor)

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun idIsEmpty() = validationIdEmpty(command, processor)
    @Test fun idBadFormat() = validationIdFormat(command, processor)

}
