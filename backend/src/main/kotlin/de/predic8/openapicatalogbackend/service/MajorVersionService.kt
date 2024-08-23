package de.predic8.openapicatalogbackend.service

import de.predic8.openapicatalogbackend.model.dto.majorVersion.LintRuleDto
import de.predic8.openapicatalogbackend.model.entity.majorVersion.LintRule
import de.predic8.openapicatalogbackend.repository.MajorVersionRepository
import de.predic8.openapicatalogbackend.repository.majorVersion.LintRuleRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class MajorVersionService(val repo: MajorVersionRepository, val repoLR: LintRuleRepository, val serviceD: DocumentService) {
    fun saveLintingRuleBy(dto: LintRuleDto, id: UUID): LintRuleDto? = repo.findByIdOrNull(id)?.let { LintRule(dto = dto, majorVersion = it) }?.let(repoLR::save)?.let(::LintRuleDto)?.also(serviceD::saveLintReports)
}
