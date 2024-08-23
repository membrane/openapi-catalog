package de.predic8.openapicatalogbackend.utils.report

import de.predic8.openapicatalogbackend.model.enums.EOS.MACOS
import de.predic8.openapicatalogbackend.model.enums.EOS.WINDOWS
import de.predic8.openapicatalogbackend.utils.Tools
import kotlinx.serialization.json.Json.Default.parseToJsonElement
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import java.io.File
import java.lang.Runtime.getRuntime
import java.time.Instant
import java.time.Instant.parse
import java.util.*

@Component
class DiffTool(val restTemplate: RestTemplate, val tools: Tools) {
    val file: File by lazy { File(tools.folder.file, name) }
    val fileLastUpdate: File by lazy { File(tools.folder.file, ".${file.name}") }

    @Value("\${tools.diff.github.name}")
    lateinit var name: String

    @Value("\${tools.diff.github.organization}")
    lateinit var organization: String

    fun generateReportBy(old: String, new: String): JsonElement? = tools.saveToTempFile(old).let { oldFile ->
        tools.saveToTempFile(new).let { newFile ->
            getRuntime().exec(arrayOf(file.path, "report", oldFile.path, newFile.path))
                .inputStream.readAllBytes().decodeToString()
                .runCatching(::parseToJsonElement).getOrNull()?.let {
                    buildJsonObject {
                        put("report", it)
                        // TODO WORKAROUND FOR https://github.com/pb33f/openapi-changes/issues/137
                        put(
                            "reportPaths",
                            getRuntime().exec(arrayOf(file.path, "summary", "-bmn", oldFile.path, newFile.path)).inputStream.readAllBytes().decodeToString()
                                .extractMarkdownPathFromSummary()
                        )
                    }
                }.also { oldFile.delete() }
                .also { newFile.delete() }
        }
    }

    fun init(): Boolean = tools.github?.getOrganization(organization)?.getRepository(name)?.latestRelease
        ?.takeIf { file.isUpdateNeeded(lastUpdate = fileLastUpdate, newUpdate = it.published_at) }
        ?.apply { fileLastUpdate.writeText(published_at.toInstant().toString()) }
        ?.listAssets()?.firstOrNull { it.name.contains(getNameByOS(), ignoreCase = true) }
        ?.run { restTemplate.getForObject<ByteArrayResource>(browserDownloadUrl) }
        ?.run { GzipCompressorInputStream(inputStream) }
        ?.let(::TarArchiveInputStream)
        ?.writeToolInFile(file)
        ?.run { file.setExecutable(true, true) } == true

    private fun String.extractMarkdownPathFromSummary(): String = substringAfter("```").substringBeforeLast("```").trim().let { "```markdown\n$it\n```" }

    private fun getNameByOS() = when (tools.detectOS()) {
        MACOS -> "darwin_x86_64"
        WINDOWS -> "windows_x86_64"
        else -> "linux_x86_64"
    }

    private fun File.isUpdateNeeded(lastUpdate: File, newUpdate: Date): Boolean = !exists() || !lastUpdate.exists() || lastUpdate.isUpdateNeeded(newUpdate)

    private fun File.isUpdateNeeded(newUpdate: Date): Boolean = parseInstant()?.let(newUpdate.toInstant()::isAfter) != false

    private fun File.parseInstant(): Instant? = readText().runCatching(::parse).getOrNull()

    private fun TarArchiveInputStream.writeToolInFile(tool: File) {
        while (nextEntry != null) if (currentEntry.name == tool.name) tool.writeBytes(readBytes())
    }
}
