package ru.otus.otuskotlin.desksharing.repository.cassandra

import com.datastax.oss.driver.api.mapper.annotations.Dao
import com.datastax.oss.driver.api.mapper.annotations.Delete
import com.datastax.oss.driver.api.mapper.annotations.Insert
import com.datastax.oss.driver.api.mapper.annotations.QueryProvider
import com.datastax.oss.driver.api.mapper.annotations.Select
import com.datastax.oss.driver.api.mapper.annotations.Update
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandFilterRequest
import ru.otus.otuskotlin.desksharing.repository.cassandra.model.DemandCassandraEntity
import java.util.concurrent.CompletionStage

@Dao
interface DemandCassandraDAO {
    @Insert
    fun create(dto: DemandCassandraEntity): CompletionStage<Unit>

    @Select
    fun read(demandId: String): CompletionStage<DemandCassandraEntity?>

    @Update(customIfClause = "lock = :prevLock")
    fun update(dto: DemandCassandraEntity, prevLock: String): CompletionStage<Boolean>

    @Delete(
        customWhereClause = "demand_id = :demandId",
        customIfClause = "lock = :prevLock",
        entityClass = [DemandCassandraEntity::class]
    )
    fun delete(demandId: String, prevLock: String): CompletionStage<Boolean>

    @QueryProvider(providerClass = DemandCassandraSearchProvider::class, entityHelpers = [DemandCassandraEntity::class])
    fun search(filter: DbDemandFilterRequest): CompletionStage<Collection<DemandCassandraEntity>>
}

