package ru.otus.otuskotlin.desksharing.spring.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
@ConfigurationPropertiesScan
class RestTemplateConfig(val config: AuthConfiguration) {

    @Bean
    fun restTemplate(): RestTemplate? {
        return RestTemplateBuilder().setReadTimeout(Duration.ofSeconds(120L))
            .setConnectTimeout(Duration.ofSeconds(20L))
            .rootUri(config.issuerUri)
            .build()
    }

}