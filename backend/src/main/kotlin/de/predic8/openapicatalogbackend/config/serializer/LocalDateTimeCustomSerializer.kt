package de.predic8.openapicatalogbackend.config.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind.STRING
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.LocalDateTime.parse

// TODO Workaround until kotlinx-datetime v0.7.0. See https://github.com/Kotlin/kotlinx-datetime/issues/350
@Component
class LocalDateTimeCustomSerializer : KSerializer<LocalDateTime> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("LocalDateTime", STRING)

    override fun deserialize(decoder: Decoder): LocalDateTime = parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        value.toString().also(encoder::encodeString)
    }
}
