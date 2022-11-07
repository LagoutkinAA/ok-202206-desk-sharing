package ru.otus.otuskotlin.desksharing.repository.cassandra

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import com.datastax.oss.driver.internal.core.type.codec.DateCodec
import com.datastax.oss.driver.internal.core.type.codec.registry.DefaultCodecRegistry
import com.datastax.oss.driver.internal.core.util.concurrent.CompletableFutures
import org.testcontainers.containers.CassandraContainer
import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.repository.IDemandRepository
import ru.otus.otuskotlin.desksharing.repository.cassandra.model.DemandCassandraEntity
import ru.otus.otuskotlin.desksharing.repository.tests.RepoDemandCreateTest
import ru.otus.otuskotlin.desksharing.repository.tests.RepoDemandDeleteTest
import ru.otus.otuskotlin.desksharing.repository.tests.RepoDemandReadTest
import ru.otus.otuskotlin.desksharing.repository.tests.RepoDemandSearchTest
import ru.otus.otuskotlin.desksharing.repository.tests.RepoDemandUpdateTest
import java.net.InetSocketAddress

class RepoDemandCassandraCreateTest : RepoDemandCreateTest() {
    override val repo: IDemandRepository = TestCompanion.repository(initObjects, "ks_create", lockNew)
}

class RepoDemandCassandraDeleteTest : RepoDemandDeleteTest() {
    override val repo: IDemandRepository = TestCompanion.repository(initObjects, "ks_delete", lockOld)
}

class RepoDemandCassandraReadTest : RepoDemandReadTest() {
    override val repo: IDemandRepository = TestCompanion.repository(initObjects, "ks_read", "")
}

class RepoDemandCassandraSearchTest : RepoDemandSearchTest() {
    override val repo: IDemandRepository = TestCompanion.repository(initObjects, "ks_search", "")
}

class RepoDemandCassandraUpdateTest : RepoDemandUpdateTest() {
    override val repo: IDemandRepository = TestCompanion.repository(initObjects, "ks_update", lockNew)
}

class TestCasandraContainer : CassandraContainer<TestCasandraContainer>("cassandra:3.11.2")

object TestCompanion {
    private val container by lazy { TestCasandraContainer().apply { start() } }

    private val codecRegistry by lazy {
        DefaultCodecRegistry("default").apply {
            register(DateCodec())
        }
    }

    private val session by lazy {
        CqlSession.builder()
            .addContactPoint(InetSocketAddress(container.host, container.getMappedPort(CassandraContainer.CQL_PORT)))
            .withLocalDatacenter("datacenter1")
            .withAuthCredentials(container.username, container.password)
            .withCodecRegistry(codecRegistry)
            .build()
    }

    private val mapper by lazy { CassandraMapper.builder(session).build() }

    private fun createSchema(keyspace: String) {
        session.execute(
            SchemaBuilder
                .createKeyspace(keyspace)
                .ifNotExists()
                .withSimpleStrategy(1)
                .build()
        )
        session.execute(DemandCassandraEntity.table(keyspace, DemandCassandraEntity.TABLE_NAME))
        session.execute(DemandCassandraEntity.employeeIdIndex(keyspace, DemandCassandraEntity.TABLE_NAME))
        session.execute(DemandCassandraEntity.bookingDateIndex(keyspace, DemandCassandraEntity.TABLE_NAME))
    }

    fun repository(initObjects: List<DemandDto>, keyspace: String, lock: String): RepoDemandCassandra {
        createSchema(keyspace)
        val dao = mapper.demandDao(keyspace, DemandCassandraEntity.TABLE_NAME)
        CompletableFutures
            .allDone(initObjects.map { dao.create(DemandCassandraEntity(it)) })
            .toCompletableFuture()
            .get()

        return RepoDemandCassandra(dao, randomUuid = { lock })
    }
}
