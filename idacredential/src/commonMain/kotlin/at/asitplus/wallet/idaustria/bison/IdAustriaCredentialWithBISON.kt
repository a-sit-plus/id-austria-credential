package at.asitplus.wallet.idaustria.bison

import at.asitplus.crypto.datatypes.io.ByteArrayBase64UrlSerializer
import at.asitplus.wallet.idaustria.IdAustriaCredential
import at.asitplus.wallet.lib.data.CredentialSubject
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

object BISONIntermediateValueSerializer : KSerializer<BISONIntermediateValues> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("BISONIntermediateValueSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: BISONIntermediateValues) =
        encoder.encodeString(Json {}.encodeToString(value))

    override fun deserialize(decoder: Decoder): BISONIntermediateValues =
        Json {}.decodeFromString(decoder.decodeString())
}

@Serializable /*TODO can we do this: (with=BpkIntermediateValueSerializer::class); without infinite loop?*/
data class BISONIntermediateValues(
    @SerialName("blinded-bkz")
    @Serializable(with = ByteArrayBase64UrlSerializer::class)
    val blindedBKZ: ByteArray,

    @SerialName("blinded-bpk")
    @Serializable(with = ByteArrayBase64UrlSerializer::class)
    val blindedBPK: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as BISONIntermediateValues

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

@Serializable
@SerialName("IdAustria2023BISON")
data class IdAustriaCredentialWithBISON(
    @SerialName("ida-credential")
    val idAustriaCredential: IdAustriaCredential,

    @SerialName("bison")
    @Serializable(with = BISONIntermediateValueSerializer::class)
    val bisonIntermediates: BISONIntermediateValues
) : CredentialSubject() {
    override val id = idAustriaCredential.id
}
