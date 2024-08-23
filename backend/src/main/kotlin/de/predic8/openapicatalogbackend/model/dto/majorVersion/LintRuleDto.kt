package de.predic8.openapicatalogbackend.model.dto.majorVersion

import de.predic8.openapicatalogbackend.model.dto.ADto
import de.predic8.openapicatalogbackend.model.dto.MajorVersionDto
import de.predic8.openapicatalogbackend.model.dto.URLDto
import de.predic8.openapicatalogbackend.model.dto.document.LintReportDto
import de.predic8.openapicatalogbackend.model.entity.majorVersion.LintRule
import kotlinx.serialization.Serializable

@Serializable
class LintRuleDto(var majorVersion: MajorVersionDto? = null, var name: String, var reports: MutableList<LintReportDto> = mutableListOf(), var url: URLDto) : ADto() {
    constructor(entity: LintRule) : this(MajorVersionDto(entity.majorVersion), name = entity.name, url = URLDto(entity.url)) {
        id = entity.id
    }
}
