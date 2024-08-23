package de.predic8.openapicatalogbackend.repository

import de.predic8.openapicatalogbackend.model.entity.Document
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DocumentRepository : JpaRepository<Document, UUID> {
    fun findFirstByMajorVersionSpecificationIdOrderByVersionDescTimestampDesc(specificationId: UUID): Document?
    fun findFirstByMajorVersionSpecificationIdAndMajorVersionVersionOrderByVersionDescTimestampDesc(specificationId: UUID, version: Int): Document?
}
