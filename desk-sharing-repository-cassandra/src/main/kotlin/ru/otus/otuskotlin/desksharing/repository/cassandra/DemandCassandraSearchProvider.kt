package ru.otus.otuskotlin.desksharing.repository.cassandra

import com.datastax.oss.driver.api.core.cql.AsyncResultSet
import com.datastax.oss.driver.api.mapper.MapperContext
import com.datastax.oss.driver.api.mapper.entity.EntityHelper
import com.datastax.oss.driver.api.querybuilder.QueryBuilder
import kotlinx.datetime.LocalDate
import ru.otus.otuskotlin.desksharing.common.NONE
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.repository.DbDemandFilterRequest
import ru.otus.otuskotlin.desksharing.repository.cassandra.model.DemandCassandraEntity
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.function.BiConsumer

class DemandCassandraSearchProvider(
    private val context: MapperContext,
    private val entityHelper: EntityHelper<DemandCassandraEntity>
) {
    fun search(filter: DbDemandFilterRequest): CompletionStage<Collection<DemandCassandraEntity>> {
        var select = entityHelper.selectStart().allowFiltering()

        if (filter.employeeId != DskShrngId.NONE) {
            select = select
                .whereColumn(DemandCassandraEntity.COLUMN_EMPLOYEE_ID)
                .isEqualTo(QueryBuilder.literal(filter.employeeId.asString()))
        }
        if (filter.dateFrom != LocalDate.NONE) {
            select = select
                .whereColumn(DemandCassandraEntity.COLUMN_BOOKING_DATE)
                .isGreaterThanOrEqualTo(QueryBuilder.literal(filter.dateFrom.toString()))
        }
        if (filter.dateTo != LocalDate.NONE) {
            select = select
                .whereColumn(DemandCassandraEntity.COLUMN_BOOKING_DATE)
                .isLessThanOrEqualTo(QueryBuilder.literal(filter.dateFrom.toString()))
        }

        val asyncFetcher = AsyncFetcher()

        context.session
            .executeAsync(select.build())
            .whenComplete(asyncFetcher)

        return asyncFetcher.stage
    }

    inner class AsyncFetcher : BiConsumer<AsyncResultSet?, Throwable?> {
        private val buffer = mutableListOf<DemandCassandraEntity>()
        private val future = CompletableFuture<Collection<DemandCassandraEntity>>()
        val stage: CompletionStage<Collection<DemandCassandraEntity>> = future

        override fun accept(resultSet: AsyncResultSet?, t: Throwable?) {
            when {
                t != null -> future.completeExceptionally(t)
                resultSet == null -> future.completeExceptionally(IllegalStateException("ResultSet should not be null"))
                else -> {
                    buffer.addAll(resultSet.currentPage().map { entityHelper.get(it, false) })
                    if (resultSet.hasMorePages())
                        resultSet.fetchNextPage().whenComplete(this)
                    else
                        future.complete(buffer)
                }
            }
        }
    }
}