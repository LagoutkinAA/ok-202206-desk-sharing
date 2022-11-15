package ru.otus.otuskotlin.desksharing.spring.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("ru.otus.otuskotlin.desksharing.auth.keycloack")
data class AuthConfiguration(
    val clientId: String,
    val authorizationGrantType: String,
    val scope: String,
    val issuerUri: String,
    val userNameAttribute: String
)

