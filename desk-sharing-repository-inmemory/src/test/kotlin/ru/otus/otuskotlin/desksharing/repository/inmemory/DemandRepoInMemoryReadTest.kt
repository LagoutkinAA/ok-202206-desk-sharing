package ru.otus.otuskotlin.desksharing.repository.inmemory

import ru.otus.otuskotlin.desksharing.repository.tests.RepoDemandReadTest

class DemandRepoInMemoryReadTest: RepoDemandReadTest() {
    override val repo = DemandRepoInMemory(
        initObjects = initObjects
    )
}
