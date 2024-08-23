package de.predic8.openapicatalogbackend.repository.document

import de.predic8.openapicatalogbackend.model.entity.document.Report
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReportRepository : JpaRepository<Report, UUID>
