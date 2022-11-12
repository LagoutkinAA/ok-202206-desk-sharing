package ru.otus.otuskotlin.desksharing.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.desksharing.biz.DemandProcessor
import ru.otus.otuskotlin.desksharing.common.model.DemandCommand
import ru.otus.otuskotlin.desksharing.common.model.DemandSettings
import ru.otus.otuskotlin.desksharing.repository.stub.DemandRepoStub
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationCreateTest {

    private val command = DemandCommand.CREATE
    private val settings by lazy {
        DemandSettings(
            repoTest = DemandRepoStub()
        )
    }
    private val processor by lazy { DemandProcessor(settings) }


    @Test fun correctBookingDate() = validationBookingDateCorrect(command, processor)
    @Test fun bookingDateIsEmpty() = validationBookingDateEmpty(command, processor)
    @Test fun bookingDateNotInRange() = validationBookingDateNotInRange(command, processor)

    @Test fun correctEmployeeId() = validationEmployeeIdCorrect(command, processor)
    @Test fun employeeIdIsEmpty() = validationEmployeeIdEmpty(command, processor)
    @Test fun employeeIdBadFormat() = validationEmployeeIdFormat(command, processor)

}

