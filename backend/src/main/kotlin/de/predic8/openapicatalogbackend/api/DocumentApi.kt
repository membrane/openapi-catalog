package de.predic8.openapicatalogbackend.api

import de.predic8.openapicatalogbackend.model.dto.DocumentDto
import de.predic8.openapicatalogbackend.service.DocumentService
import org.springframework.web.bind.annotation.*
import java.util.*

@RequestMapping("/api/v1/documents")
@RestController
class DocumentApi(val service: DocumentService) {
    @GetMapping("/{id}")
    fun getBy(@PathVariable id: UUID): DocumentDto? = service.getBy(id)

    @PostMapping
    fun save(@RequestPart(value = "file") document: String, @RequestParam specificationId: UUID): List<DocumentDto> = service.saveContents(contents = listOf(document), specificationId = specificationId)
}
