package ru.otus.otuskotlin.desksharing.spring.api.v1.controller

import org.springframework.security.oauth2.jwt.Jwt
import ru.otus.otuskotlin.desksharing.common.asList
import ru.otus.otuskotlin.desksharing.common.model.DemandUserId
import ru.otus.otuskotlin.desksharing.common.permission.DemandPrincipalModel
import ru.otus.otuskotlin.desksharing.common.permission.DemandUserGroups
import ru.otus.otuskotlin.desksharing.spring.config.WebSecurityConfig.Companion.F_NAME_CLAIM
import ru.otus.otuskotlin.desksharing.spring.config.WebSecurityConfig.Companion.GROUPS_CLAIM
import ru.otus.otuskotlin.desksharing.spring.config.WebSecurityConfig.Companion.ID_CLAIM
import ru.otus.otuskotlin.desksharing.spring.config.WebSecurityConfig.Companion.L_NAME_CLAIM
import ru.otus.otuskotlin.desksharing.spring.config.WebSecurityConfig.Companion.M_NAME_CLAIM

fun Jwt?.toModel() = this?.run {
    DemandPrincipalModel(
        id = claims[ID_CLAIM]?.toString()?.let { DemandUserId(it) } ?: DemandUserId.NONE,
        fname = claims[F_NAME_CLAIM]?.toString() ?: "",
        mname = claims[M_NAME_CLAIM]?.toString() ?: "",
        lname = claims[L_NAME_CLAIM]?.toString() ?: "",
        groups = claims[GROUPS_CLAIM]
            ?.asList(String::class.java)
            ?.mapNotNull {
                when(it) {
                    "USER" -> DemandUserGroups.USER
                    else -> null
                }
            }?.toSet() ?: emptySet()
    )
} ?: DemandPrincipalModel.NONE

