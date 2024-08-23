package de.predic8.openapicatalogbackend.utils.report

import de.predic8.openapicatalogbackend.model.entity.majorVersion.LintRule
import de.predic8.openapicatalogbackend.model.enums.EOS.MACOS
import de.predic8.openapicatalogbackend.model.enums.EOS.WINDOWS
import de.predic8.openapicatalogbackend.utils.Tools
import kotlinx.serialization.json.Json.Default.parseToJsonElement
import kotlinx.serialization.json.JsonElement
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.io.File
import java.lang.Runtime.getRuntime
import java.time.Instant
import java.time.Instant.parse
import java.util.*

@Component
class LintTool(val restTemplate: RestTemplate, val tools: Tools) {
    val file: File by lazy { File(tools.folder.file, name) }
    val fileLastUpdate: File by lazy { File(tools.folder.file, ".${file.name}") }

    @Value("\${tools.lint.github.name}")
    lateinit var name: String

    @Value("\${tools.lint.github.organization}")
    lateinit var organization: String

    fun generateReportBy(content: String, lintRule: LintRule? = null): JsonElement = lintRule?.url?.url
        .let { tools.saveToTempFile(content = "extends: ['${it ?: "spectral:oas"}']", fileEnding = ".yaml") }
        .let { generateReportBy(content = content, fileRuleSet = it) }
        .let(::parseToJsonElement)

    fun init(): Boolean = tools.github?.getOrganization(organization)?.getRepository(name)?.latestRelease
        ?.takeIf { file.isUpdateNeeded(lastUpdate = fileLastUpdate, newUpdate = it.published_at) }
        ?.apply { fileLastUpdate.writeText(published_at.toInstant().toString()) }
        ?.listAssets()?.firstOrNull { it.name.contains(getNameByOS(), ignoreCase = true) }
        ?.run { restTemplate.getForObject<ByteArray>(browserDownloadUrl) }
        ?.let(file::writeBytes)
        ?.run { file.setExecutable(true, true) } == true

    private fun generateReportBy(content: String, fileRuleSet: File): String = tools.createTempFile().let {
        tools.saveToTempFile(content)
            .apply { getRuntime().exec(arrayOf(file.path, "lint", path, "-f", "json", "-o", it.path, "-r", fileRuleSet.path)).waitFor() }
            .apply(File::delete)
            .apply { fileRuleSet.delete() }
            .run { it.readText() }
            .apply { it.delete() }
    }

    private fun getNameByOS() = when (tools.detectOS()) {
        MACOS -> "macos-x64"
        WINDOWS -> ".exe"
        else -> "linux-x64"
    }

    private fun File.isUpdateNeeded(lastUpdate: File, newUpdate: Date): Boolean = !exists() || !lastUpdate.exists() || lastUpdate.isUpdateNeeded(newUpdate)

    private fun File.isUpdateNeeded(newUpdate: Date): Boolean = parseInstant()?.let(newUpdate.toInstant()::isAfter) != false

    private fun File.parseInstant(): Instant? = readText().runCatching(::parse).getOrNull()
}
