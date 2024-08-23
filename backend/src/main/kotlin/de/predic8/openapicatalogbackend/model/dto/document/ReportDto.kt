package de.predic8.openapicatalogbackend.model.dto.document

import de.predic8.openapicatalogbackend.model.dto.ADto
import de.predic8.openapicatalogbackend.model.entity.document.Report
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json.Default.parseToJsonElement
import kotlinx.serialization.json.JsonElement

@Serializable
class ReportDto(var diffReport: JsonElement? = null, var lintReports: MutableList<LintReportDto> = mutableListOf()) : ADto() {
    constructor(entity: Report) : this(diffReport = entity.diffReport?.let(::parseToJsonElement), lintReports = entity.lintReports.map(::LintReportDto).toMutableList()) {
        id = entity.id
    }
}