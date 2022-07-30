package model

import java.time.LocalDateTime
import java.util.*

abstract class BaseEntity(
    id: UUID?,
    var createTime: LocalDateTime? = null,
    var updateTime: LocalDateTime? = null
) {
    private val id: UUID = id ?: UUID.randomUUID()

    @Transient
    private val entityNew: Boolean = id == null
}