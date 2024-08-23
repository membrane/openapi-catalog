package de.predic8.openapicatalogbackend.init

import de.predic8.openapicatalogbackend.service.SpecificationService
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.core.Ordered.HIGHEST_PRECEDENCE
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
class InitTasks(val specificationService: SpecificationService) {
    @EventListener
    @Order(HIGHEST_PRECEDENCE)
    fun onApplicationEventCreateFolder(event: ContextRefreshedEvent) {
        specificationService.enableAllTasks()
    }
}
