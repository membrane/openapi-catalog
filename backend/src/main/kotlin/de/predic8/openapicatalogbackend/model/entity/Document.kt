package de.predic8.openapicatalogbackend.model.entity

import de.predic8.openapicatalogbackend.model.dto.DocumentDto
import de.predic8.openapicatalogbackend.model.entity.document.Report
import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL
import java.time.LocalDateTime
import java.time.LocalDateTime.now

@Entity
class Document(
    @Lob
    var content: String,
    @ManyToOne(optional = false)
    var majorVersion: MajorVersion,
    var openapiVersion: String,
    @OneToOne(cascade = [ALL])
    var report: Report,
    @ElementCollection
    val servers: MutableMap<String, String> = mutableMapOf(),
    var timestamp: LocalDateTime = now(),
    var title: String,
    var version: String,
) : AEntity() {
    constructor(dto: DocumentDto, majorVersion: MajorVersion, report: Report) : this(
        content = dto.content,
        majorVersion = majorVersion,
        openapiVersion = dto.openapiVersion,
        report = report,
        servers = dto.servers,
        timestamp = dto.timestamp,
        title = dto.title,
        version = dto.version
    )
}
