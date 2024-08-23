package de.predic8.openapicatalogbackend.repository

import de.predic8.openapicatalogbackend.model.entity.Specification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SpecificationRepository : JpaRepository<Specification, UUID>