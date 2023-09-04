package at.asitplus.wallet.idaustria

import at.asitplus.wallet.lib.jws.ByteArrayBase64UrlSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

object BpkIntermediateValueSerializer : KSerializer<BpkIntermediateValues> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("BpkIntermediateValueSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: BpkIntermediateValues) =
        encoder.encodeString(Json {}.encodeToString(value))

    override fun deserialize(decoder: Decoder): BpkIntermediateValues =
        Json{}.decodeFromString(decoder.decodeString())
}

@Serializable/*TODO can we do this: (with=BpkIntermediateValueSerializer::class); without infinite loop?*/
data class BpkIntermediateValues(
    @SerialName("blinded-bkz")
    @Serializable(with = ByteArrayBase64UrlSerializer::class)
    val blindedBKZ: ByteArray,

    @SerialName("blinded-bpk")
    @Serializable(with = ByteArrayBase64UrlSerializer::class)
    val blindedBPK: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BpkIntermediateValues

        if (!blindedBKZ.contentEquals(other.blindedBKZ)) return false
        if (!blindedBPK.contentEquals(other.blindedBPK)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = blindedBKZ.contentHashCode()
        result = 31 * result + blindedBPK.contentHashCode()
        return result
    }
}