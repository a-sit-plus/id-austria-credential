package at.asitplus.wallet.idaustria

import at.asitplus.crypto.datatypes.io.ByteArrayBase64UrlSerializer
import at.asitplus.wallet.idaustria.IdAustriaScheme.Attributes
import at.asitplus.wallet.lib.data.CredentialSubject
import kotlinx.datetime.LocalDate
import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("IdAustria2023")
data class IdAustriaCredential(

    override val id: String,

    @SerialName(Attributes.BPK)
    val bpk: String,

    @SerialName(Attributes.FIRSTNAME)
    val firstname: String,

    @SerialName(Attributes.LASTNAME)
    val lastname: String,

    @SerialName(Attributes.DATE_OF_BIRTH)
    @Serializable(with = LocalDateIso8601Serializer::class)
    val dateOfBirth: LocalDate,

    @SerialName(Attributes.PORTRAIT)
    @Serializable(with = ByteArrayBase64UrlSerializer::class)
    val portrait: ByteArray? = null,

    /**
     * See [https://eid.egiz.gv.at/meldeadresse/](https://eid.egiz.gv.at/meldeadresse/)
     */
    @SerialName(Attributes.MAIN_ADDRESS)
    val mainAddress: String? = null,

    @SerialName(Attributes.AGE_OVER_14)
    val ageOver14: Boolean? = null,

    @SerialName(Attributes.AGE_OVER_16)
    val ageOver16: Boolean? = null,

    @SerialName(Attributes.AGE_OVER_18)
    val ageOver18: Boolean? = null,

    @SerialName(Attributes.AGE_OVER_21)
    val ageOver21: Boolean? = null,

    ) : CredentialSubject() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as IdAustriaCredential

        if (id != other.id) return false
        if (bpk != other.bpk) return false
        if (firstname != other.firstname) return false
        if (lastname != other.lastname) return false
        if (dateOfBirth != other.dateOfBirth) return false
        if (portrait != null) {
            if (other.portrait == null) return false
            if (!portrait.contentEquals(other.portrait)) return false
        } else if (other.portrait != null) return false
        if (mainAddress != other.mainAddress) return false
        if (ageOver14 != other.ageOver14) return false
        if (ageOver16 != other.ageOver16) return false
        if (ageOver18 != other.ageOver18) return false
        if (ageOver21 != other.ageOver21) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + bpk.hashCode()
        result = 31 * result + firstname.hashCode()
        result = 31 * result + lastname.hashCode()
        result = 31 * result + dateOfBirth.hashCode()
        result = 31 * result + (portrait?.contentHashCode() ?: 0)
        result = 31 * result + (mainAddress?.hashCode() ?: 0)
        result = 31 * result + (ageOver14?.hashCode() ?: 0)
        result = 31 * result + (ageOver16?.hashCode() ?: 0)
        result = 31 * result + (ageOver18?.hashCode() ?: 0)
        result = 31 * result + (ageOver21?.hashCode() ?: 0)
        return result
    }

}
