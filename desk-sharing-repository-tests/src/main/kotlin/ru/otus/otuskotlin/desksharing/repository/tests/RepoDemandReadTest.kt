package ru.otus.otuskotlin.desksharing.repository.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandIdRequest
import ru.otus.otuskotlin.desksharing.common.repository.IDemandRepository
import ru.otus.otuskotlin.marketplace.backend.repo.tests.runRepoTest
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoDemandReadTest {
    abstract val repo: IDemandRepository

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readDemand(DbDemandIdRequest(successId))

        assertEquals(true, result.isSuccess)
        assertEquals(readSuccessStub, result.data)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readDemand(DbDemandIdRequest(notFoundId))

        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code == "not-found" }
        assertEquals("demandId", error?.field)
    }

    companion object : BaseInitDemands("delete") {
        override val initObjects: List<DemandDto> = listOf(
            createInitTestModel("read")
        )
        private val readSuccessStub = initObjects.first()

        val successId = DskShrngId(readSuccessStub.demandId.asString())
        val notFoundId = DskShrngId("ad-repo-read-notFound")

    }
}
