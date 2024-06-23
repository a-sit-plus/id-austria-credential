package at.asitplus.wallet.idaustria

import at.asitplus.wallet.lib.data.ConstantIndex
import at.asitplus.wallet.lib.data.ConstantIndex.CredentialRepresentation
import at.asitplus.wallet.lib.data.ConstantIndex.CredentialRepresentation.*
import at.asitplus.wallet.lib.data.SchemaIndex


object IdAustriaScheme : ConstantIndex.CredentialScheme {
    override val schemaUri: String = "${SchemaIndex.BASE}/schemas/1.0.0/idaustria.json"
    override val vcType: String = "IdAustria2023"
    override val isoNamespace: String = "at.gv.id-austria.2023"
    override val isoDocType: String = "at.gv.id-austria.2023.iso"
    override val sdJwtType: String = "at.gv.id-austria.2023.1"
    override val supportedRepresentations: Collection<CredentialRepresentation> = listOf(SD_JWT, PLAIN_JWT, ISO_MDOC)
    override val claimNames: Collection<String> = listOf(
        Attributes.BPK,
        Attributes.FIRSTNAME,
        Attributes.LASTNAME,
        Attributes.DATE_OF_BIRTH,
        Attributes.PORTRAIT,
        Attributes.MAIN_ADDRESS,
        Attributes.AGE_OVER_14,
        Attributes.AGE_OVER_16,
        Attributes.AGE_OVER_18,
        Attributes.AGE_OVER_21,
        Attributes.VEHICLE_REGISTRATION,
        Attributes.GENDER,
    )

    object Attributes {
        const val BPK = "bpk"
        const val FIRSTNAME = "firstname"
        const val LASTNAME = "lastname"
        const val DATE_OF_BIRTH = "date-of-birth"
        const val PORTRAIT = "portrait"
        const val MAIN_ADDRESS = "main-address"
        const val AGE_OVER_14 = "age-over-14"
        const val AGE_OVER_16 = "age-over-16"
        const val AGE_OVER_18 = "age-over-18"
        const val AGE_OVER_21 = "age-over-21"
        const val VEHICLE_REGISTRATION = "vehicle-registration"
        const val GENDER = "gender"
    }
}
