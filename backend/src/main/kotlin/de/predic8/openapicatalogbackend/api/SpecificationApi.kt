package de.predic8.openapicatalogbackend.api

import de.predic8.openapicatalogbackend.model.dto.MajorVersionDto
import de.predic8.openapicatalogbackend.model.dto.SpecificationDto
import de.predic8.openapicatalogbackend.model.dto.SpecificationTmpDto
import de.predic8.openapicatalogbackend.service.SpecificationService
import org.springframework.web.bind.annotation.*
import java.util.*

@RequestMapping("/api/v1/specifications")
@RestController
class SpecificationApi(val service: SpecificationService) {
    @GetMapping
    fun getAll(): List<SpecificationDto> = service.getAll()

    @GetMapping("/{id}")
    fun getBy(@PathVariable id: UUID): SpecificationDto? = service.getBy(id)

    @GetMapping("/{id}/majorversions/{version}")
    fun getMajorVersionBy(@PathVariable id: UUID, @PathVariable version: Int): MajorVersionDto? = service.getMajorVersionBy(id = id, version = version)

    @PostMapping
    fun save(@RequestBody dto: SpecificationTmpDto): SpecificationDto = service.save(dto)
}
