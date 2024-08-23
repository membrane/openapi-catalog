package de.predic8.openapicatalogbackend.init

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.safari.SafariDriver
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.core.Ordered.LOWEST_PRECEDENCE
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
class OpenBrowser(val sp: ServerProperties) {
    @EventListener(condition = "@environment.acceptsProfiles('prod')")
    @Order(LOWEST_PRECEDENCE)
    fun onApplicationEventCreateFolder(event: ContextRefreshedEvent) {
        val rwd: RemoteWebDriver? = runCatching { ChromeDriver() }.getOrNull()
            ?: runCatching { FirefoxDriver() }.getOrNull()
            ?: runCatching { SafariDriver() }.getOrNull()
            ?: runCatching { EdgeDriver() }.getOrNull()
            ?: runCatching { InternetExplorerDriver() }.getOrNull()

        rwd?.get("http://localhost:" + sp.port)
    }
}
