package ru.otus.otuskotlin.desksharing.repository.inmemory

import ru.otus.otuskotlin.desksharing.repository.tests.RepoDemandSearchTest

class DemandRepoInMemorySearchTest : RepoDemandSearchTest() {
    override val repo = DemandRepoInMemory(
        initObjects = initObjects
    )
}
