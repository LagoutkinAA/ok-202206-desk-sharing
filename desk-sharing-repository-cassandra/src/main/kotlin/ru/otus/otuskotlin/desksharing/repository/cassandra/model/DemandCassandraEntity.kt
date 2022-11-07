package ru.otus.otuskotlin.desksharing.repository.cassandra.model

import com.datastax.oss.driver.api.core.type.DataTypes
import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import ru.otus.otuskotlin.desksharing.common.fromJavaLocalDate
import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DemandStatus
import ru.otus.otuskotlin.desksharing.common.model.DemandUserId
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.model.WorkDeskNumber
import ru.otus.otuskotlin.desksharing.common.toJavaLocalDate
import java.time.LocalDate

@Entity
data class DemandCassandraEntity(
    @field:CqlName(COLUMN_ID)
    @field:PartitionKey
    var demandId: String? = null,
    @field:CqlName(COLUMN_DATE)
    var date: LocalDate? = null,
    @field:CqlName(COLUMN_BOOKING_DATE)
    var bookingDate: LocalDate? = null,
    @field:CqlName(COLUMN_EMPLOYEE_ID)
    var employeeId: String? = null,
    @field:CqlName(COLUMN_STATUS)
    var status: String? = null,
    @field:CqlName(COLUMN_NUMBER)
    var number: String? = null,
    @field:CqlName(COLUMN_WORK_DESK_NUMBER)
    var workDeskNumber: String? = null,
    @field:CqlName(COLUMN_DECLINE_REASON)
    var declineReason: String? = null,
    @field:CqlName(COLUMN_USER_ID)
    var userId: String? = null,
    @field:CqlName(COLUMN_LOCK)
    var lock: String? = null
) {
    constructor(model: DemandDto) : this(
        demandId = model.demandId.asString().takeIf { it.isNotBlank() },
        date = kotlinx.datetime.LocalDate.toJavaLocalDate(model.date),
        bookingDate = kotlinx.datetime.LocalDate.toJavaLocalDate(model.bookingDate),
        employeeId = model.employeeId.asString().takeIf { it.isNotBlank() },
        status = model.status.name.takeIf { it.isNotBlank() },
        number = model.number.takeIf { it.isNotBlank() },
        workDeskNumber = model.workDeskNumber.asString().takeIf { it.isNotBlank() },
        declineReason = model.declineReason.takeIf { it.isNotBlank() },
        userId = model.userId.asString().takeIf { it.isNotBlank() },
        lock = model.lock.takeIf { it.isNotBlank() }
    )

    fun toInternal() = DemandDto(
        date = kotlinx.datetime.LocalDate.fromJavaLocalDate(date),
        bookingDate = kotlinx.datetime.LocalDate.fromJavaLocalDate(bookingDate),
        employeeId = employeeId?.let { DskShrngId(it) } ?: DskShrngId.NONE,
        status = status?.let { DemandStatus.valueOf(it) } ?: DemandStatus.NONE,
        number = number ?: "",
        workDeskNumber = workDeskNumber?.let { WorkDeskNumber(it) } ?: WorkDeskNumber.NONE,
        declineReason = declineReason ?: "",
        demandId = demandId?.let { DskShrngId(it) } ?: DskShrngId.NONE,
        userId = userId?.let { DemandUserId(it) } ?: DemandUserId.NONE,
        lock = lock ?: ""
    )

    companion object {
        const val TABLE_NAME = "demands"

        const val COLUMN_ID = "demand_id"
        const val COLUMN_DATE = "date"
        const val COLUMN_BOOKING_DATE = "booking_date"
        const val COLUMN_EMPLOYEE_ID = "employee_id"
        const val COLUMN_STATUS = "status"
        const val COLUMN_NUMBER = "number"
        const val COLUMN_WORK_DESK_NUMBER = "work_desk_number"
        const val COLUMN_DECLINE_REASON = "decline_reason"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_LOCK = "lock"

        fun table(keyspace: String, tableName: String) =
            SchemaBuilder
                .createTable(keyspace, tableName)
                .ifNotExists()
                .withPartitionKey(COLUMN_ID, DataTypes.TEXT)
                .withColumn(COLUMN_DATE, DataTypes.DATE)
                .withColumn(COLUMN_BOOKING_DATE, DataTypes.DATE)
                .withColumn(COLUMN_EMPLOYEE_ID, DataTypes.TEXT)
                .withColumn(COLUMN_STATUS, DataTypes.TEXT)
                .withColumn(COLUMN_NUMBER, DataTypes.TEXT)
                .withColumn(COLUMN_WORK_DESK_NUMBER, DataTypes.TEXT)
                .withColumn(COLUMN_DECLINE_REASON, DataTypes.TEXT)
                .withColumn(COLUMN_USER_ID, DataTypes.TEXT)
                .withColumn(COLUMN_LOCK, DataTypes.TEXT)
                .build()

        fun bookingDateIndex(keyspace: String, tableName: String, locale: String = "en") =
            SchemaBuilder
                .createIndex()
                .ifNotExists()
                .usingSASI()
                .onTable(keyspace, tableName)
                .andColumn(COLUMN_BOOKING_DATE)
                .withSASIOptions(mapOf("mode" to "CONTAINS", "tokenization_locale" to locale))
                .build()

        fun employeeIdIndex(keyspace: String, tableName: String, locale: String = "en") =
            SchemaBuilder
                .createIndex()
                .ifNotExists()
                .usingSASI()
                .onTable(keyspace, tableName)
                .andColumn(COLUMN_EMPLOYEE_ID)
                .withSASIOptions(mapOf("mode" to "CONTAINS", "tokenization_locale" to locale))
                .build()
    }
}
