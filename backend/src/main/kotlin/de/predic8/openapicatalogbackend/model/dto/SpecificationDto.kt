package de.predic8.openapicatalogbackend.model.dto

import de.predic8.openapicatalogbackend.model.dto.specification.UpdateDto
import de.predic8.openapicatalogbackend.model.entity.Specification
import kotlinx.serialization.Serializable

@Serializable
data class SpecificationDto(val majorVersions: MutableList<MajorVersionDto> = mutableListOf(), var update: UpdateDto? = null) : ADto() {
    constructor(entity: Specification) : this(update = entity.update?.let(::UpdateDto)) {
        id = entity.id
    }
}
