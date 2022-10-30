package ru.otus.otuskotlin.desksharing.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.desksharing.biz.DemandProcessor
import ru.otus.otuskotlin.desksharing.common.model.DemandCommand
import ru.otus.otuskotlin.desksharing.common.model.DemandSettings
import ru.otus.otuskotlin.desksharing.repository.stub.DemandRepoStub
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationDeleteTest {

    private val command = DemandCommand.DELETE
    private val settings by lazy {
        DemandSettings(
            repoTest = DemandRepoStub()
        )
    }
    private val processor by lazy { DemandProcessor(settings) }


    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun idIsEmpty() = validationIdEmpty(command, processor)
    @Test fun idBadFormat() = validationIdFormat(command, processor)

}

