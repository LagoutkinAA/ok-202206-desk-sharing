package ru.otus.otuskotlin.desksharing.repository.inmemory

import ru.otus.otuskotlin.desksharing.repository.tests.RepoDemandCreateTest

class DemandRepoInMemoryCreateTest : RepoDemandCreateTest() {
    override val repo = DemandRepoInMemory(
        initObjects = initObjects,
        randomUuid = { lockNew }
    )
}
