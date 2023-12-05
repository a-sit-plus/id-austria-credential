package at.asitplus.wallet.idaustria

import at.asitplus.wallet.lib.data.ConstantIndex
import at.asitplus.wallet.lib.data.SchemaIndex


object IdAustriaScheme : ConstantIndex.CredentialScheme {
    override val schemaUri: String = "${SchemaIndex.BASE}/schemas/1.0.0/idaustria.json"
    override val vcType: String = "IdAustria2023"
    override val isoNamespace: String = "at.gv.id-austria.2023"
    override val isoDocType: String = "at.gv.id-austria.2023.iso"
    override val claimNames: Collection<String> =
        listOf(Attributes.FIRSTNAME, Attributes.LASTNAME, Attributes.DATE_OF_BIRTH, Attributes.PORTRAIT)

    object Attributes {
        const val FIRSTNAME = "firstname"
        const val LASTNAME = "lastname"
        const val DATE_OF_BIRTH = "date-of-birth"
        const val PORTRAIT = "portrait"
    }
}
