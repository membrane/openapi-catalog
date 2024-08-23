package de.predic8.openapicatalogbackend.model.dto

import de.predic8.openapicatalogbackend.config.serializer.UUIDStringSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
abstract class ADto(@Serializable(with = UUIDStringSerializer::class) var id: UUID = UUID(0, 0))
