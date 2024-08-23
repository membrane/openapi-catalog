package de.predic8.openapicatalogbackend.model.entity.specification

import de.predic8.openapicatalogbackend.model.dto.specification.UpdateDto
import de.predic8.openapicatalogbackend.model.entity.AEntity
import de.predic8.openapicatalogbackend.model.entity.URL
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.Entity
import jakarta.persistence.OneToOne

@Entity
class Update(var interval: Int = 30, var path: String? = null, @OneToOne(cascade = [ALL]) var url: URL) : AEntity() {
    constructor(dto: UpdateDto) : this(interval = dto.interval, path = dto.path, url = URL(dto.url))
}
