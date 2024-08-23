package de.predic8.openapicatalogbackend.config.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind.STRING
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.springframework.stereotype.Component
import java.util.*
import java.util.UUID.fromString

@Component
class UUIDStringSerializer : KSerializer<UUID> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("UUID", STRING)

    override fun deserialize(decoder: Decoder): UUID = decoder.decodeString().takeIf(String::isNotBlank)?.let(::fromString) ?: UUID(0, 0)

    override fun serialize(encoder: Encoder, value: UUID) {
        value.toString().also(encoder::encodeString)
    }
}
