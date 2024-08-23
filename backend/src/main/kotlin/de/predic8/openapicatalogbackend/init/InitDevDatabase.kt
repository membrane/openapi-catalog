package de.predic8.openapicatalogbackend.init

import de.predic8.openapicatalogbackend.api.SpecificationApi
import de.predic8.openapicatalogbackend.model.dto.DocumentDto
import de.predic8.openapicatalogbackend.model.dto.SpecificationTmpDto
import de.predic8.openapicatalogbackend.model.dto.URLDto
import de.predic8.openapicatalogbackend.model.dto.specification.UpdateDto
import de.predic8.openapicatalogbackend.model.enums.EType.GIT
import de.predic8.openapicatalogbackend.model.enums.EType.HTTP
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.core.annotation.Order
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.File

@Component
class InitDevDatabase(val api: SpecificationApi) {
    @Value("init")
    lateinit var resourceFolder: ClassPathResource

    @EventListener(condition = "@environment.acceptsProfiles('dev')")
    @Order
    fun onApplicationEvent(event: ContextRefreshedEvent) {
        addSpecs()

        addAutoUpdaterSpecsByGIT()
        addAutoUpdaterSpecsByHTTP()
    }

    private fun addAutoUpdaterSpecsByGIT() {
        SpecificationTmpDto(
            update = UpdateDto(
                interval = 60,
                path = "examples/v3.1/non-oauth-scopes.yaml",
                url = URLDto(
                    url = "https://github.com/OAI/OpenAPI-Specification.git",
                    type = GIT
                ),
            )
        ).let(api::save)

        SpecificationTmpDto(
            update = UpdateDto(
                interval = 60,
                path = "examples/v3.1/webhook-example.yaml",
                url = URLDto(
                    url = "https://github.com/OAI/OpenAPI-Specification.git",
                    type = GIT,
                )
            )
        ).let(api::save)

        SpecificationTmpDto(
            update = UpdateDto(
                interval = 60,
                path = "openapi.yaml",
                url = URLDto(
                    url = "https://github.com/Redocly/openapi-template.git",
                    type = GIT,
                )
            )
        ).let(api::save)
    }

    private fun addAutoUpdaterSpecsByHTTP() {
        SpecificationTmpDto(
            update = UpdateDto(
                interval = 60,
                url = URLDto(
                    url = "https://raw.githubusercontent.com/OAI/OpenAPI-Specification/main/examples/v3.1/non-oauth-scopes.yaml",
                    type = HTTP,
                )
            )
        ).let(api::save)

        SpecificationTmpDto(
            update = UpdateDto(
                interval = 60,
                url = URLDto(
                    url = "https://api.predic8.de/api-docs/fruit-shop-api-v2-2-0",
                    type = HTTP,
                )
            )
        ).let(api::save)
    }

    private fun addSpecs(): Unit = resourceFolder.file.walk().maxDepth(1).filter { it.name != resourceFolder.filename }.forEach { folder ->
        folder.walk().filter(File::isFile).filter { it.extension == "yaml" }.map { DocumentDto(it.readText()) }.toList().let(::SpecificationTmpDto).let(api::save)
    }
}
