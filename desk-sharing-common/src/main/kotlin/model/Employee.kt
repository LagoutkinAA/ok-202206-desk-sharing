package model

import java.util.*

data class Employee(
    var id: UUID? = null,
    var number: Int,
    var firstName: String,
    var middleName: String?,
    var lastName: String,
    var position: String,
    var gender: Gender
) : BaseEntity(id)
