package de.predic8.openapicatalogbackend.beans

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler


@Configuration
@EnableScheduling
class ThreadPoolTaskSchedulerConfig {
    @Bean
    fun threadPoolTaskScheduler(): ThreadPoolTaskScheduler = ThreadPoolTaskScheduler().apply {
        poolSize = 5
        @Suppress("UsePropertyAccessSyntax")
        // It is causing the error "'val' cannot be reassigned."
        setThreadNamePrefix("ThreadPoolTaskScheduler")
    }
}
