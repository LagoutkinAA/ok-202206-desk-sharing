package ru.otus.otuskotlin.desksharing.repository.inmemory

import ru.otus.otuskotlin.desksharing.repository.tests.RepoDemandDeleteTest

class DemandRepoInMemoryDeleteTest : RepoDemandDeleteTest() {
    override val repo = DemandRepoInMemory(
        initObjects = initObjects
    )
}
