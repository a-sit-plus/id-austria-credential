package at.asitplus.wallet.idaustria

import at.asitplus.wallet.idaustria.IdAustriaScheme.Attributes
import at.asitplus.wallet.lib.data.CredentialSubject
import at.asitplus.wallet.lib.jws.ByteArrayBase64UrlSerializer
import kotlinx.datetime.LocalDate
import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("IdAustria2023")
data class IdAustriaCredential(

    override val id: String,

    @SerialName(Attributes.FIRSTNAME)
    val firstname: String,

    @SerialName(Attributes.LASTNAME)
    val lastname: String,

    @SerialName(Attributes.DATE_OF_BIRTH)
    @Serializable(with = LocalDateIso8601Serializer::class)
    val dateOfBirth: LocalDate,

    @SerialName(Attributes.PORTRAIT)
    @Serializable(with = ByteArrayBase64UrlSerializer::class)
    val portrait: ByteArray? = null

) : CredentialSubject() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as IdAustriaCredential

        if (id != other.id) return false
        if (firstname != other.firstname) return false
        if (lastname != other.lastname) return false
        if (dateOfBirth != other.dateOfBirth) return false
        if (portrait != null) {
            if (other.portrait == null) return false
            if (!portrait.contentEquals(other.portrait)) return false
        } else if (other.portrait != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + firstname.hashCode()
        result = 31 * result + lastname.hashCode()
        result = 31 * result + dateOfBirth.hashCode()
        result = 31 * result + (portrait?.contentHashCode() ?: 0)
        return result
    }
}
