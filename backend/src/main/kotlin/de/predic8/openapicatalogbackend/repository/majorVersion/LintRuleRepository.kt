package de.predic8.openapicatalogbackend.repository.majorVersion

import de.predic8.openapicatalogbackend.model.entity.majorVersion.LintRule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LintRuleRepository : JpaRepository<LintRule, UUID> {
    fun findAllByMajorVersionId(majorVersionId: UUID): List<LintRule>
}
