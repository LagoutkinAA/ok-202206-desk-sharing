package entity

import java.util.*

data class Branch(
    var id: UUID?,
    var number: String,
    var name: String,
    var address: Address
) : BaseEntity(id) {
    data class Address(
        var city: String,
        var street: String
    )
}
