package de.predic8.openapicatalogbackend.model.entity.document

import de.predic8.openapicatalogbackend.model.dto.document.ReportDto
import de.predic8.openapicatalogbackend.model.entity.AEntity
import jakarta.persistence.CascadeType.ALL
import jakarta.persistence.Entity
import jakarta.persistence.Lob
import jakarta.persistence.OneToMany

@Entity
class Report(@Lob var diffReport: String? = null, @OneToMany(cascade = [ALL], mappedBy = "report", orphanRemoval = true) var lintReports: MutableList<LintReport> = mutableListOf()) : AEntity() {
    constructor(dto: ReportDto) : this(diffReport = dto.diffReport?.toString())
}
