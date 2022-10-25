package ru.otus.otuskotlin.desksharing.repository.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandRequest
import ru.otus.otuskotlin.desksharing.common.repository.IDemandRepository
import ru.otus.otuskotlin.marketplace.backend.repo.tests.runRepoTest
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoDemandCreateTest {
    abstract val repo: IDemandRepository

    protected open val lockNew: String = "20000000-0000-0000-0000-000000000002"

    protected val createObj = DemandDto(

    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createDemand(DbDemandRequest(createObj))
        val expected = createObj.copy(demandId = result.data?.demandId ?: DskShrngId.NONE)
        assertEquals(true, result.isSuccess)
        assertNotEquals(DskShrngId.NONE, result.data?.demandId)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    companion object : BaseInitDemands("create") {
        override val initObjects: List<DemandDto> = emptyList()
    }
}
