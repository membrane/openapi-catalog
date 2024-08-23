package de.predic8.openapicatalogbackend.model.dto

import de.predic8.openapicatalogbackend.config.serializer.LocalDateTimeCustomSerializer
import de.predic8.openapicatalogbackend.model.dto.document.ReportDto
import de.predic8.openapicatalogbackend.model.entity.Document
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.LocalDateTime.now

@Serializable
data class DocumentDto(
    var content: String,
    var majorVersion: MajorVersionDto? = null,
    var openapiVersion: String = "",
    var report: ReportDto? = null,
    val servers: MutableMap<String, String> = mutableMapOf(),
    @Serializable(with = LocalDateTimeCustomSerializer::class)
    var timestamp: LocalDateTime = now(),
    var title: String = "",
    var version: String = "",
) :
    ADto() {
    constructor(entity: Document, content: String = "", majorVersion: MajorVersionDto? = null) : this(
        content = content,
        majorVersion = majorVersion,
        openapiVersion = entity.openapiVersion,
        report = ReportDto(entity.report),
        servers = entity.servers,
        timestamp = entity.timestamp,
        title = entity.title,
        version = entity.version
    ) {
        id = entity.id
    }
}
