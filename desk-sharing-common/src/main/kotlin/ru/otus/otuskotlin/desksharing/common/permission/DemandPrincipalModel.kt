package ru.otus.otuskotlin.desksharing.common.permission

import ru.otus.otuskotlin.desksharing.common.model.DemandUserId

data class DemandPrincipalModel(
    val id: DemandUserId = DemandUserId.NONE,
    val fname: String = "",
    val mname: String = "",
    val lname: String = "",
    val groups: Set<DemandUserGroups> = emptySet()
) {
    companion object {
        val NONE = DemandPrincipalModel()
    }
}
