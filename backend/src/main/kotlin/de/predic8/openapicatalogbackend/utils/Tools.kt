package de.predic8.openapicatalogbackend.utils

import de.predic8.openapicatalogbackend.model.enums.EOS
import de.predic8.openapicatalogbackend.model.enums.EOS.*
import org.apache.commons.lang3.SystemUtils.OS_NAME
import org.kohsuke.github.GitHub
import org.kohsuke.github.GitHub.connectAnonymously
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.system.ApplicationTemp
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Component
import java.io.File
import java.util.UUID.randomUUID

@Component
class Tools(private val tmp: ApplicationTemp = ApplicationTemp()) {
    @Value("\${user.home}/.predic8/openapicatalog/tools/")
    lateinit var folder: FileSystemResource

    val github: GitHub?
        get() = runCatching { connectAnonymously() }.getOrNull()?.takeIf { it.rateLimit.getRemaining() > 0 }

    fun createFolder(): Boolean = folder.file.mkdirs()

    fun createTempFile(fileEnding: String = ""): File = File(tmp.dir, "${randomUUID()}$fileEnding")

    fun createTempFolder(): File = tmp.getDir(randomUUID().toString())

    fun detectOS(): EOS = when {
        OS_NAME.contains("Mac", true) -> MACOS
        OS_NAME.contains("Windows", true) -> WINDOWS
        else -> LINUX
    }


    fun saveToTempFile(content: String, fileEnding: String = ""): File = createTempFile(fileEnding).apply { writeText(content) }
}