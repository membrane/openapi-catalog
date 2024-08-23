package de.predic8.openapicatalogbackend.model.dto.specification

import de.predic8.openapicatalogbackend.model.dto.ADto
import de.predic8.openapicatalogbackend.model.dto.URLDto
import de.predic8.openapicatalogbackend.model.entity.specification.Update
import kotlinx.serialization.Serializable

@Serializable
data class UpdateDto(var interval: Int = 30, var path: String? = null, var url: URLDto) : ADto() {
    constructor(entity: Update) : this(interval = entity.interval, path = entity.path, url = URLDto(entity.url)) {
        id = entity.id
    }
}
