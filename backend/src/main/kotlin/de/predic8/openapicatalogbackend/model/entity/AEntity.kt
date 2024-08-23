package de.predic8.openapicatalogbackend.model.entity

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.AUTO
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.util.*

@MappedSuperclass
abstract class AEntity(@Id @GeneratedValue(strategy = AUTO) val id: UUID = UUID(0, 0))
