package de.predic8.openapicatalogbackend.model.dto

import de.predic8.openapicatalogbackend.model.dto.majorVersion.LintRuleDto
import de.predic8.openapicatalogbackend.model.entity.MajorVersion
import kotlinx.serialization.Serializable

@Serializable
class MajorVersionDto(
    val documents: MutableList<DocumentDto> = mutableListOf(),
    var lintRules: MutableList<LintRuleDto> = mutableListOf(),
    var specification: SpecificationDto,
    var version: Int
) : ADto() {
    constructor(entity: MajorVersion) : this(specification = SpecificationDto(entity.specification), version = entity.version) {
        id = entity.id
    }
}
