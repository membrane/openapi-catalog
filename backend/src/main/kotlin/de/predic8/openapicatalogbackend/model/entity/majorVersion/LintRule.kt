package de.predic8.openapicatalogbackend.model.entity.majorVersion

import de.predic8.openapicatalogbackend.model.dto.majorVersion.LintRuleDto
import de.predic8.openapicatalogbackend.model.entity.AEntity
import de.predic8.openapicatalogbackend.model.entity.MajorVersion
import de.predic8.openapicatalogbackend.model.entity.URL
import de.predic8.openapicatalogbackend.model.entity.document.LintReport
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
class LintRule(
    @OneToMany(cascade = [ALL], mappedBy = "rule", orphanRemoval = true)
    var reports: MutableList<LintReport> = mutableListOf(),
    @ManyToOne(optional = false)
    var majorVersion: MajorVersion,
    var name: String,
    @OneToOne(cascade = [ALL])
    var url: URL,
) : AEntity() {
    constructor(dto: LintRuleDto, majorVersion: MajorVersion) : this(majorVersion = majorVersion, name = dto.name, url = URL(dto.url))
}
