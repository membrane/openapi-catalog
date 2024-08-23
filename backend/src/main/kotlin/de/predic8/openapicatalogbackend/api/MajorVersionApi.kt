package de.predic8.openapicatalogbackend.api

import de.predic8.openapicatalogbackend.model.dto.majorVersion.LintRuleDto
import de.predic8.openapicatalogbackend.service.MajorVersionService
import org.springframework.web.bind.annotation.*
import java.util.*

@RequestMapping("/api/v1/majorversions")
@RestController
class MajorVersionApi(val service: MajorVersionService) {
    @PostMapping("/{id}/lintingrules")
    fun saveLintingRuleBy(@PathVariable id: UUID, @RequestBody dto: LintRuleDto): LintRuleDto? = service.saveLintingRuleBy(dto = dto, id = id)
}
