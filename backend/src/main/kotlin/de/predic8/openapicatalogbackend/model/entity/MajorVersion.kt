package de.predic8.openapicatalogbackend.model.entity

import de.predic8.openapicatalogbackend.model.entity.majorVersion.LintRule
import jakarta.persistence.*
import jakarta.persistence.CascadeType.ALL

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["specification_id", "version"])])
class MajorVersion(
    @OneToMany(cascade = [ALL], mappedBy = "majorVersion", orphanRemoval = true)
    @OrderBy("version DESC, timestamp DESC")
    val documents: MutableList<Document> = mutableListOf(),
    @OneToMany(cascade = [ALL], mappedBy = "majorVersion", orphanRemoval = true)
    var lintRules: MutableList<LintRule> = mutableListOf(),
    @ManyToOne(optional = false)
    var specification: Specification,
    var version: Int,
) : AEntity()
