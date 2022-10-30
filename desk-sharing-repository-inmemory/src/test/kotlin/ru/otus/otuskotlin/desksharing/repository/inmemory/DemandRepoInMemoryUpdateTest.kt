package ru.otus.otuskotlin.desksharing.repository.inmemory

import ru.otus.otuskotlin.desksharing.repository.tests.RepoDemandUpdateTest

class DemandRepoInMemoryUpdateTest : RepoDemandUpdateTest() {
    override val repo = DemandRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew }
    )
}
