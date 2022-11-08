package ru.otus.otuskotlin.desksharing.auth

import ru.otus.otuskotlin.desksharing.common.model.DemandDto
import ru.otus.otuskotlin.desksharing.common.model.DskShrngId
import ru.otus.otuskotlin.desksharing.common.permission.DemandPrincipalModel
import ru.otus.otuskotlin.desksharing.common.permission.DemandPrincipalRelations

fun DemandDto.resolveRelationsTo(principal: DemandPrincipalModel): Set<DemandPrincipalRelations> = setOfNotNull(
    DemandPrincipalRelations.NONE,
    DemandPrincipalRelations.NEW.takeIf { demandId == DskShrngId.NONE },
    DemandPrincipalRelations.OWN.takeIf { principal.id == userId }
)
