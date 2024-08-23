package de.predic8.openapicatalogbackend.model.dto.document

import de.predic8.openapicatalogbackend.model.dto.ADto
import de.predic8.openapicatalogbackend.model.dto.majorVersion.LintRuleDto
import de.predic8.openapicatalogbackend.model.entity.document.LintReport
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json.Default.parseToJsonElement
import kotlinx.serialization.json.JsonElement

@Serializable
class LintReportDto(var content: JsonElement, var rule: LintRuleDto? = null) : ADto() {
    constructor(entity: LintReport) : this(content = entity.content.let(::parseToJsonElement), rule = entity.rule?.let(::LintRuleDto)) {
        id = entity.id
    }
}