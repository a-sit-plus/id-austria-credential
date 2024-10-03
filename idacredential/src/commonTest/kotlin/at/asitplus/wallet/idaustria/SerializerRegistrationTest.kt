package at.asitplus.wallet.idaustria

import at.asitplus.signum.indispensable.cosef.CoseHeader
import at.asitplus.signum.indispensable.cosef.CoseSigned
import at.asitplus.signum.indispensable.cosef.io.ByteStringWrapper
import at.asitplus.wallet.idaustria.IdAustriaScheme.Attributes.AGE_OVER_14
import at.asitplus.wallet.idaustria.IdAustriaScheme.Attributes.AGE_OVER_16
import at.asitplus.wallet.idaustria.IdAustriaScheme.Attributes.AGE_OVER_18
import at.asitplus.wallet.idaustria.IdAustriaScheme.Attributes.AGE_OVER_21
import at.asitplus.wallet.idaustria.IdAustriaScheme.Attributes.BPK
import at.asitplus.wallet.idaustria.IdAustriaScheme.Attributes.DATE_OF_BIRTH
import at.asitplus.wallet.idaustria.IdAustriaScheme.Attributes.FIRSTNAME
import at.asitplus.wallet.idaustria.IdAustriaScheme.Attributes.GENDER
import at.asitplus.wallet.idaustria.IdAustriaScheme.Attributes.LASTNAME
import at.asitplus.wallet.idaustria.IdAustriaScheme.Attributes.MAIN_ADDRESS
import at.asitplus.wallet.idaustria.IdAustriaScheme.Attributes.PORTRAIT
import at.asitplus.wallet.idaustria.IdAustriaScheme.Attributes.VEHICLE_REGISTRATION
import at.asitplus.wallet.lib.agent.SubjectCredentialStore
import at.asitplus.wallet.lib.data.CredentialToJsonConverter
import at.asitplus.wallet.lib.iso.IssuerSigned
import at.asitplus.wallet.lib.iso.IssuerSignedItem
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.JsonObject
import kotlin.random.Random
import kotlin.random.nextUInt

class SerializerRegistrationTest : FreeSpec({

    "Serialization and deserialization" - {
        withData(nameFn = { "for ${it.key}" }, dataMap().entries) {
            val serialized = it.toIssuerSignedItem().serialize(IdAustriaScheme.isoNamespace)

            val deserialized = IssuerSignedItem.deserialize(serialized, IdAustriaScheme.isoNamespace).getOrThrow()

            deserialized.elementValue shouldBe it.value
        }
    }

    "Serialization to JSON Element" {
        val claims = dataMap()
        val namespacedItems = mapOf(IdAustriaScheme.isoNamespace to claims.map { it.toIssuerSignedItem() }.toList())
        val issuerAuth = CoseSigned(ByteStringWrapper(CoseHeader()), null, null, byteArrayOf())
        val credential = SubjectCredentialStore.StoreEntry.Iso(
            IssuerSigned.fromIssuerSignedItems(namespacedItems, issuerAuth),
            IdAustriaScheme
        )
        val converted = CredentialToJsonConverter.toJsonElement(credential)
            .shouldBeInstanceOf<JsonObject>()

        val jsonMap = converted[IdAustriaScheme.isoNamespace]
            .shouldBeInstanceOf<JsonObject>()

        claims.forEach {
            withClue("Serialization for ${it.key}") {
                jsonMap[it.key].shouldNotBeNull()
            }
        }
    }
})

private fun Map.Entry<String, Any>.toIssuerSignedItem() =
    IssuerSignedItem(Random.nextUInt(), Random.nextBytes(32), key, value)


private fun dataMap(): Map<String, Any> =
    mapOf(
        BPK to randomString(),
        FIRSTNAME to randomString(),
        LASTNAME to randomString(),
        DATE_OF_BIRTH to randomLocalDate(),
        PORTRAIT to Random.nextBytes(32),
        MAIN_ADDRESS to randomString(),
        AGE_OVER_14 to Random.nextBoolean(),
        AGE_OVER_16 to Random.nextBoolean(),
        AGE_OVER_18 to Random.nextBoolean(),
        AGE_OVER_21 to Random.nextBoolean(),
        VEHICLE_REGISTRATION to randomString(),
        GENDER to randomString()
    )

private fun randomLocalDate() = LocalDate(Random.nextInt(1900, 2100), Random.nextInt(1, 12), Random.nextInt(1, 28))

