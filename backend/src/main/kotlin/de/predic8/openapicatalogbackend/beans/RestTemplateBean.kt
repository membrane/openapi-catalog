package de.predic8.openapicatalogbackend.beans

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateBean(val builder: RestTemplateBuilder) {
    @Bean
    fun restTemplate(): RestTemplate = builder.build()
}
