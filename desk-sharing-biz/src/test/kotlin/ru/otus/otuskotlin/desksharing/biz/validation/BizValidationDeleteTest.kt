package ru.otus.otuskotlin.desksharing.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.desksharing.biz.DemandProcessor
import ru.otus.otuskotlin.desksharing.common.model.DemandCommand
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationDeleteTest {

    private val command = DemandCommand.DELETE
    private val processor by lazy { DemandProcessor() }

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun idIsEmpty() = validationIdEmpty(command, processor)
    @Test fun idBadFormat() = validationIdFormat(command, processor)

}

