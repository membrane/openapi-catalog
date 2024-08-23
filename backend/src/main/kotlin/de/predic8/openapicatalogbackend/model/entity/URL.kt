package de.predic8.openapicatalogbackend.model.entity

import de.predic8.openapicatalogbackend.model.dto.URLDto
import de.predic8.openapicatalogbackend.model.enums.EType
import de.predic8.openapicatalogbackend.model.enums.EType.GIT
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated

@Entity
class URL(var url: String, @Enumerated(STRING) var type: EType = GIT) : AEntity() {
    constructor(dto: URLDto) : this(url = dto.url, type = dto.type)
}
