package ru.otus.otuskotlin.desksharing.auth

import ru.otus.otuskotlin.desksharing.common.model.DemandCommand
import ru.otus.otuskotlin.desksharing.common.permission.DemandPrincipalRelations
import ru.otus.otuskotlin.desksharing.common.permission.DemandUserPermissions

fun checkPermitted(
    command: DemandCommand,
    relations: Iterable<DemandPrincipalRelations>,
    permissions: Iterable<DemandUserPermissions>,
) =
    relations.asSequence().flatMap { relation ->
        permissions.map { permission ->
            AccessTableConditions(
                command = command,
                permission = permission,
                relation = relation,
            )
        }
    }.any {
        accessTable[it] != null
    }

private data class AccessTableConditions(
    val command: DemandCommand,
    val permission: DemandUserPermissions,
    val relation: DemandPrincipalRelations
)

private val accessTable = mapOf(
    // Create
    AccessTableConditions(
        command = DemandCommand.CREATE,
        permission = DemandUserPermissions.CREATE_OWN,
        relation = DemandPrincipalRelations.NEW,
    ) to true,

    // Read
    AccessTableConditions(
        command = DemandCommand.READ,
        permission = DemandUserPermissions.READ_OWN,
        relation = DemandPrincipalRelations.OWN,
    ) to true,
    AccessTableConditions(
        command = DemandCommand.READ,
        permission = DemandUserPermissions.READ_PUBLIC,
        relation = DemandPrincipalRelations.PUBLIC,
    ) to true,

    // Update
    AccessTableConditions(
        command = DemandCommand.UPDATE,
        permission = DemandUserPermissions.UPDATE_OWN,
        relation = DemandPrincipalRelations.OWN,
    ) to true,

    // Delete
    AccessTableConditions(
        command = DemandCommand.DELETE,
        permission = DemandUserPermissions.DELETE_OWN,
        relation = DemandPrincipalRelations.OWN,
    ) to true,

    // Search
    AccessTableConditions(
        command = DemandCommand.SEARCH,
        permission = DemandUserPermissions.SEARCH_OWN,
        relation = DemandPrincipalRelations.OWN,
    ) to true,
)
