package ru.otus.otuskotlin.desksharing.auth

import ru.otus.otuskotlin.desksharing.common.permission.DemandUserGroups
import ru.otus.otuskotlin.desksharing.common.permission.DemandUserPermissions

fun resolveChainPermissions(
    groups: Iterable<DemandUserGroups>,
) = mutableSetOf<DemandUserPermissions>()
    .apply {
        addAll(groups.flatMap { groupPermissionsAdmits[it] ?: emptySet() })
        removeAll(groups.flatMap { groupPermissionsDenys[it] ?: emptySet() }.toSet())
    }
    .toSet()

private val groupPermissionsAdmits = mapOf(
    DemandUserGroups.USER to setOf(
        DemandUserPermissions.READ_OWN,
        DemandUserPermissions.READ_PUBLIC,
        DemandUserPermissions.CREATE_OWN,
        DemandUserPermissions.UPDATE_OWN,
        DemandUserPermissions.DELETE_OWN,
    ),
    DemandUserGroups.MODERATOR_DEMAND to setOf(),
    DemandUserGroups.ADMIN_DEMAND to setOf(),
    DemandUserGroups.TEST to setOf(),
    DemandUserGroups.BAN_DEMAND to setOf(),
)

private val groupPermissionsDenys = mapOf(
    DemandUserGroups.USER to setOf(),
    DemandUserGroups.MODERATOR_DEMAND to setOf(),
    DemandUserGroups.ADMIN_DEMAND to setOf(),
    DemandUserGroups.TEST to setOf(),
    DemandUserGroups.BAN_DEMAND to setOf(
        DemandUserPermissions.UPDATE_OWN,
        DemandUserPermissions.CREATE_OWN,
    ),
)
