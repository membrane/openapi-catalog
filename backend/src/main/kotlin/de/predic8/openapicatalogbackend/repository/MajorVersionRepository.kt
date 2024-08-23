package de.predic8.openapicatalogbackend.repository

import de.predic8.openapicatalogbackend.model.entity.MajorVersion
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MajorVersionRepository : JpaRepository<MajorVersion, UUID> {
    fun findFirstBySpecificationIdAndVersion(specificationId: UUID, version: Int): MajorVersion?
}
