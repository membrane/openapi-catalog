package de.predic8.openapicatalogbackend.service

import com.charleskorn.kaml.Yaml.Companion.default
import com.charleskorn.kaml.YamlList
import com.charleskorn.kaml.YamlMap
import com.charleskorn.kaml.YamlNode
import com.charleskorn.kaml.yamlMap
import de.predic8.openapicatalogbackend.model.dto.DocumentDto
import de.predic8.openapicatalogbackend.model.dto.MajorVersionDto
import de.predic8.openapicatalogbackend.model.dto.majorVersion.LintRuleDto
import de.predic8.openapicatalogbackend.model.entity.Document
import de.predic8.openapicatalogbackend.model.entity.MajorVersion
import de.predic8.openapicatalogbackend.model.entity.Specification
import de.predic8.openapicatalogbackend.model.entity.document.LintReport
import de.predic8.openapicatalogbackend.model.entity.document.Report
import de.predic8.openapicatalogbackend.model.entity.majorVersion.LintRule
import de.predic8.openapicatalogbackend.repository.DocumentRepository
import de.predic8.openapicatalogbackend.repository.MajorVersionRepository
import de.predic8.openapicatalogbackend.repository.SpecificationRepository
import de.predic8.openapicatalogbackend.repository.document.LintReportRepository
import de.predic8.openapicatalogbackend.repository.document.ReportRepository
import de.predic8.openapicatalogbackend.repository.majorVersion.LintRuleRepository
import de.predic8.openapicatalogbackend.utils.report.DiffTool
import de.predic8.openapicatalogbackend.utils.report.LintTool
import kotlinx.serialization.json.Json.Default.parseToJsonElement
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.semver4j.Semver.coerce
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class DocumentService(
    val repo: DocumentRepository,
    val repoMV: MajorVersionRepository,
    val repoR: ReportRepository,
    val repoLRe: LintReportRepository,
    val repoLRu: LintRuleRepository,
    val repoS: SpecificationRepository,
    val toolD: DiffTool,
    val toolL: LintTool,
) {
    fun getBy(id: UUID): DocumentDto? = repo.findByIdOrNull(id)?.let { DocumentDto(content = it.content, entity = it, majorVersion = MajorVersionDto(it.majorVersion)) }

    fun save(allowDuplicate: Boolean = true, documents: List<DocumentDto>, specificationId: UUID): List<DocumentDto> =
        repoS.findByIdOrNull(specificationId)?.let { documents.save(allowDuplicate = allowDuplicate, specification = it) }.orEmpty()

    fun saveContents(allowDuplicate: Boolean = true, contents: List<String>, specificationId: UUID): List<DocumentDto> =
        save(allowDuplicate = allowDuplicate, documents = contents.map(::DocumentDto), specificationId = specificationId)

    fun saveLintReports(lt: LintRuleDto): List<LintReport> = runCatching { repoLRu.getReferenceById(lt.id) }.getOrNull()?.run { majorVersion.documents.map { (it.generateReportBy(this)) } }.orEmpty()

    private fun DocumentDto.createChangesReportBy(specificationId: UUID): JsonElement? = coerce(version)?.major
        ?.let { repo.findFirstByMajorVersionSpecificationIdAndMajorVersionVersionOrderByVersionDescTimestampDesc(specificationId = specificationId, version = it) }
        ?.let { toolD.generateReportBy(old = it.content, new = content) }

    private fun DocumentDto.fillWith(yamlMap: YamlMap): DocumentDto = apply {
        openapiVersion = yamlMap.getScalarContentBy("openapi", "swagger")
        yamlMap.get<YamlList>("servers")?.items?.map(YamlNode::yamlMap)
            ?.mapNotNull { it.getScalarContentOrNullBy("description")?.let { desc -> it.getScalarContentBy("url") to desc } }
            ?.let(servers::putAll)
        title = yamlMap.get<YamlMap>("info")?.getScalarContentBy("title").orEmpty()
        version = yamlMap.get<YamlMap>("info")?.getScalarContentBy("version")?.let(::coerce)?.version.orEmpty()
    }

    private fun DocumentDto.findOrCreateMajorVersion(specification: Specification): MajorVersion? =
        coerce(version)?.major?.let { v -> repoMV.findFirstBySpecificationIdAndVersion(specificationId = specification.id, version = v) ?: repoMV.save(MajorVersion(specification = specification, version = v)) }

    private fun YamlMap.getScalarContentBy(vararg keys: String = arrayOf("null")): String =
        getScalarContentOrNullBy(keys = keys) ?: error("${path.toHumanReadableString()}.${keys.joinToString("/")} not specified.")

    private fun YamlMap.getScalarContentOrNullBy(vararg keys: String = arrayOf("null")): String? = keys.firstNotNullOfOrNull(::getScalar)?.content

    private fun Report.hasChanges(): Boolean =
        diffReport?.runCatching(::parseToJsonElement)?.getOrNull()?.jsonObject?.get("report")?.jsonObject?.get("message")?.jsonPrimitive?.contentOrNull != "No changes found between specifications"

    private fun DocumentDto.parseDocument(): DocumentDto = fillWith(content.parseToYamlNode().yamlMap)

    private fun String.parseToYamlNode(): YamlNode = let(default::parseToYamlNode)

    private fun DocumentDto.save(allowDuplicate: Boolean, specification: Specification): DocumentDto? =
        parseDocument().findOrCreateMajorVersion(specification = specification)?.let { saveReports(allowDuplicate = allowDuplicate, mv = it, s = specification) }?.let(repo::save)?.let(::DocumentDto)

    private fun List<DocumentDto>.save(allowDuplicate: Boolean, specification: Specification): List<DocumentDto> = mapNotNull { it.save(allowDuplicate = allowDuplicate, specification = specification) }

    private fun DocumentDto.saveReports(allowDuplicate: Boolean, mv: MajorVersion, s: Specification): Document? = Report(createChangesReportBy(s.id)?.toString())
        .takeIf { allowDuplicate || it.hasChanges() }
        ?.let(repoR::save)
        ?.also { saveLintReports(mv = mv, r = it) }
        ?.let { Document(dto = this, majorVersion = mv, report = it) }

    private fun DocumentDto.saveLintReports(mv: MajorVersion, r: Report): List<LintReport> = repoLRu.findAllByMajorVersionId(mv.id)
        .map { LintReport(content = toolL.generateReportBy(content = content, lintRule = it).toString(), report = r, rule = it) }
        .ifEmpty { listOf(LintReport(content = toolL.generateReportBy(content).toString(), report = r)) }
        .let(repoLRe::saveAll)

    private fun Document.generateReportBy(lr: LintRule): LintReport = toolL.generateReportBy(content = content, lintRule = lr).toString().let { LintReport(content = it, report = report, rule = lr) }.let(repoLRe::save)
}
