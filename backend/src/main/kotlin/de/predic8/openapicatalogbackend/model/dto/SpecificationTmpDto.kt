package de.predic8.openapicatalogbackend.model.dto

import de.predic8.openapicatalogbackend.model.dto.specification.UpdateDto
import kotlinx.serialization.Serializable

@Serializable
data class SpecificationTmpDto(val documents: List<DocumentDto> = emptyList(), val update: UpdateDto? = null) : ADto()
