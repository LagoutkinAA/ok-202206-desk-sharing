package ru.otus.otuskotlin.desksharing.biz.validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.otus.otuskotlin.desksharing.biz.DemandProcessor
import ru.otus.otuskotlin.desksharing.common.DemandContext
import ru.otus.otuskotlin.desksharing.common.model.DemandCommand
import ru.otus.otuskotlin.desksharing.common.model.DemandFilter
import ru.otus.otuskotlin.desksharing.common.model.DemandSettings
import ru.otus.otuskotlin.desksharing.common.model.DemandState
import ru.otus.otuskotlin.desksharing.common.model.DskShrngWorkMode
import ru.otus.otuskotlin.desksharing.repository.stub.DemandRepoStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {

    private val command = DemandCommand.SEARCH
    private val settings by lazy {
        DemandSettings(
            repoTest = DemandRepoStub()
        )
    }
    private val processor by lazy { DemandProcessor(settings) }


    @Test
    fun correctEmpty() = runTest {
        val ctx = DemandContext(
            command = command,
            state = DemandState.NONE,
            workMode = DskShrngWorkMode.TEST,
            demandFilterRequest = DemandFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(DemandState.FAILING, ctx.state)
    }

}

