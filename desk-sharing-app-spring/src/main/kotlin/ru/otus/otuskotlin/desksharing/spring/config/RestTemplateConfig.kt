package ru.otus.otuskotlin.desksharing.spring.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
class RestTemplateConfig(val clientRegistration: ClientRegistration) {

    @Bean
    open fun restTemplate(): RestTemplate? {
        return RestTemplateBuilder().setReadTimeout(Duration.ofSeconds(120L))
            .setConnectTimeout(Duration.ofSeconds(20L))
            .rootUri(clientRegistration.providerDetails.authorizationUri)
            .build()
    }

}