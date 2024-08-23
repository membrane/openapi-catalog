package de.predic8.openapicatalogbackend.model.entity.document

import de.predic8.openapicatalogbackend.model.entity.AEntity
import de.predic8.openapicatalogbackend.model.entity.majorVersion.LintRule
import jakarta.persistence.Entity
import jakarta.persistence.Lob
import jakarta.persistence.ManyToOne

@Entity
class LintReport(@Lob var content: String, @ManyToOne(optional = false) var report: Report, @ManyToOne var rule: LintRule? = null) : AEntity()
