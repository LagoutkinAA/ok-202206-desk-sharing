package ru.otus.otuskotlin.desksharing.spring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.otus.otuskotlin.desksharing.biz.DemandProcessor

@Configuration
class ServiceConfig {
    @Bean
    fun processor(): DemandProcessor = DemandProcessor()

}
