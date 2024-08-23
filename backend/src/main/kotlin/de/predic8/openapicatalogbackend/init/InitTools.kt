package de.predic8.openapicatalogbackend.init

import de.predic8.openapicatalogbackend.utils.Tools
import de.predic8.openapicatalogbackend.utils.report.DiffTool
import de.predic8.openapicatalogbackend.utils.report.LintTool
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.core.Ordered.HIGHEST_PRECEDENCE
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
class InitTools(val toolD: DiffTool, val toolL: LintTool, val tools: Tools) {
    @EventListener
    @Order(HIGHEST_PRECEDENCE)
    fun onApplicationEventCreateFolder(event: ContextRefreshedEvent) {
        tools.createFolder()
    }

    @EventListener
    @Order(HIGHEST_PRECEDENCE + 1)
    fun onApplicationEventDownloadDiffTool(event: ContextRefreshedEvent) {
        toolD.init()
        toolL.init()
    }
}
