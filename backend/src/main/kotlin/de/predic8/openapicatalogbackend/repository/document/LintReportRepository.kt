package de.predic8.openapicatalogbackend.repository.document

import de.predic8.openapicatalogbackend.model.entity.document.LintReport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LintReportRepository : JpaRepository<LintReport, UUID>
