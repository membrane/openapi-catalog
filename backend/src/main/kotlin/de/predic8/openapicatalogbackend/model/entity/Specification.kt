package de.predic8.openapicatalogbackend.model.entity

import de.predic8.openapicatalogbackend.model.dto.SpecificationTmpDto
import de.predic8.openapicatalogbackend.model.entity.specification.Update
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.OrderBy

@Entity
class Specification(
    @OneToMany(cascade = [ALL], mappedBy = "specification", orphanRemoval = true)
    @OrderBy("version DESC")
    val majorVersions: MutableList<MajorVersion> = mutableListOf(),
    @OneToOne(cascade = [ALL])
    var update: Update? = null,
) : AEntity() {
    constructor(dto: SpecificationTmpDto) : this(update = dto.update?.let(::Update))
}
