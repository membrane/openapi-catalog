package de.predic8.openapicatalogbackend.model.dto

import de.predic8.openapicatalogbackend.model.entity.URL
import de.predic8.openapicatalogbackend.model.enums.EType
import de.predic8.openapicatalogbackend.model.enums.EType.GIT
import kotlinx.serialization.Serializable

@Serializable
data class URLDto(var url: String, var type: EType = GIT) : ADto() {
    constructor(entity: URL) : this(url = entity.url, type = entity.type) {
        id = entity.id
    }
}
