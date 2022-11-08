package ru.otus.otuskotlin.desksharing.auth

import ru.otus.otuskotlin.desksharing.common.permission.DemandPermissionClient
import ru.otus.otuskotlin.desksharing.common.permission.DemandPrincipalRelations
import ru.otus.otuskotlin.desksharing.common.permission.DemandUserPermissions

fun resolveFrontPermissions(
    permissions: Iterable<DemandUserPermissions>,
    relations: Iterable<DemandPrincipalRelations>,
) = mutableSetOf<DemandPermissionClient>()
    .apply {
        for (permission in permissions) {
            for (relation in relations) {
                accessTable[permission]?.get(relation)?.let { this@apply.add(it) }
            }
        }
    }
    .toSet()

private val accessTable = mapOf(
    // READ
    DemandUserPermissions.READ_OWN to mapOf(
        DemandPrincipalRelations.OWN to DemandPermissionClient.READ
    ),
    DemandUserPermissions.READ_GROUP to mapOf(
        DemandPrincipalRelations.GROUP to DemandPermissionClient.READ
    ),
    DemandUserPermissions.READ_PUBLIC to mapOf(
        DemandPrincipalRelations.PUBLIC to DemandPermissionClient.READ
    ),
    DemandUserPermissions.READ_CANDIDATE to mapOf(
        DemandPrincipalRelations.MODERATABLE to DemandPermissionClient.READ
    ),

    // UPDATE
    DemandUserPermissions.UPDATE_OWN to mapOf(
        DemandPrincipalRelations.OWN to DemandPermissionClient.UPDATE
    ),
    DemandUserPermissions.UPDATE_PUBLIC to mapOf(
        DemandPrincipalRelations.MODERATABLE to DemandPermissionClient.UPDATE
    ),
    DemandUserPermissions.UPDATE_CANDIDATE to mapOf(
        DemandPrincipalRelations.MODERATABLE to DemandPermissionClient.UPDATE
    ),

    // DELETE
    DemandUserPermissions.DELETE_OWN to mapOf(
        DemandPrincipalRelations.OWN to DemandPermissionClient.DELETE
    ),
    DemandUserPermissions.DELETE_PUBLIC to mapOf(
        DemandPrincipalRelations.MODERATABLE to DemandPermissionClient.DELETE
    ),
    DemandUserPermissions.DELETE_CANDIDATE to mapOf(
        DemandPrincipalRelations.MODERATABLE to DemandPermissionClient.DELETE
    ),
)
