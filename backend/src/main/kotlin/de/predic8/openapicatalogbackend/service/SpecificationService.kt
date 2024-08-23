package de.predic8.openapicatalogbackend.service

import de.predic8.openapicatalogbackend.model.dto.DocumentDto
import de.predic8.openapicatalogbackend.model.dto.MajorVersionDto
import de.predic8.openapicatalogbackend.model.dto.SpecificationDto
import de.predic8.openapicatalogbackend.model.dto.SpecificationTmpDto
import de.predic8.openapicatalogbackend.model.dto.specification.UpdateDto
import de.predic8.openapicatalogbackend.model.entity.MajorVersion
import de.predic8.openapicatalogbackend.model.entity.Specification
import de.predic8.openapicatalogbackend.model.enums.EType.GIT
import de.predic8.openapicatalogbackend.repository.DocumentRepository
import de.predic8.openapicatalogbackend.repository.MajorVersionRepository
import de.predic8.openapicatalogbackend.repository.SpecificationRepository
import de.predic8.openapicatalogbackend.utils.GitTool
import de.predic8.openapicatalogbackend.utils.TaskSchedulerTool
import kotlinx.coroutines.Runnable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.net.URI
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.ScheduledFuture
import kotlin.time.toDuration
import kotlin.time.toJavaDuration
import java.util.concurrent.TimeUnit as TU
import kotlin.time.DurationUnit as DU

@Service
class SpecificationService(val repo: SpecificationRepository, val repoD: DocumentRepository, val repoMV: MajorVersionRepository, val serviceD: DocumentService, val toolG: GitTool, val toolTS: TaskSchedulerTool) {
    fun createTaskBy(dto: SpecificationDto): ScheduledFuture<*>? = dto.update?.run { toTask(dto.id) }

    fun enableAllTasks(): List<ScheduledFuture<*>> = repo.findAll().map(::SpecificationDto).mapNotNull(::createTaskBy)

    fun getAll(): List<SpecificationDto> = repo.findAll().map { it.toDto() }

    fun getBy(id: UUID): SpecificationDto? = repo.findByIdOrNull(id)?.toDto()

    fun getMajorVersionBy(id: UUID, version: Int): MajorVersionDto? = repoMV.findFirstBySpecificationIdAndVersion(id, version)?.toDto()

    fun save(dto: SpecificationTmpDto): SpecificationDto = dto.saveSpecification().also { createTaskBy(it)?.runCatching { get(30, TU.SECONDS) } ?: serviceD.save(documents = dto.documents, specificationId = it.id) }

    private fun UpdateDto.getDocuments(lastFetch: LocalDateTime?): List<String> = when (url.type) {
        GIT -> toolG.getDocumentsBy(lastFetch = lastFetch, update = this)
        else -> URI(url.url).toURL().readText().let(::listOf)
    }

    private fun SpecificationTmpDto.saveSpecification(): SpecificationDto = let(::Specification).let(repo::save).let(::SpecificationDto)

    private fun MajorVersion.toDto(): MajorVersionDto = MajorVersionDto(this).also { it.documents.addAll(documents.map(::DocumentDto)) }

    private fun Specification.toDto(): SpecificationDto = SpecificationDto(this).also { it.majorVersions.addAll(majorVersions.map { mv -> mv.toDto() }) }

    private fun UpdateDto.toTask(id: UUID): ScheduledFuture<*> = toolTS.createScheduledTaskFrom(
        duration = interval.toDuration(DU.SECONDS).toJavaDuration(),
        runnable = Runnable { serviceD.saveContents(allowDuplicate = false, contents = getDocuments(repoD.findFirstByMajorVersionSpecificationIdOrderByVersionDescTimestampDesc(id)?.timestamp), specificationId = id) }
    )
}
