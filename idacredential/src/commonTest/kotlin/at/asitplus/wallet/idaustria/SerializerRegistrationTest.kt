package at.asitplus.wallet.idaustria

import at.asitplus.signum.indispensable.cosef.CoseHeader
import at.asitplus.signum.indispensable.cosef.CoseSigned
import at.asitplus.signum.indispensable.cosef.io.ByteStringWrapper
import at.asitplus.wallet.idaustria.IdAustriaScheme.Attributes.DATE_OF_BIRTH
import at.asitplus.wallet.idaustria.IdAustriaScheme.Attributes.FIRSTNAME
import at.asitplus.wallet.idaustria.IdAustriaScheme.Attributes.PORTRAIT
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
import io.matthewnelson.encoding.base16.Base16
import io.matthewnelson.encoding.core.Encoder.Companion.encodeToString
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.JsonObject
import kotlin.random.Random
import kotlin.random.nextUInt

class SerializerRegistrationTest : FreeSpec({

    "Serialization and deserialization" - {
        withData(nameFn = { "for ${it.key}" }, dataMap().entries) {
            val item = IssuerSignedItem(
                digestId = Random.nextUInt(),
                random = Random.nextBytes(32),
                elementIdentifier = it.key,
                elementValue = it.value
            )

            val serialized = item.serialize(IdAustriaScheme.isoNamespace)

            val deserialized = IssuerSignedItem.deserialize(serialized, IdAustriaScheme.isoNamespace).getOrThrow()

            deserialized.elementValue shouldBe it.value
        }
    }

    "Serialization to JSON Element" {
        val claims = dataMap()
        val namespacedItems: Map<String, List<IssuerSignedItem>> =
            mapOf(IdAustriaScheme.isoNamespace to claims.map {
                IssuerSignedItem(Random.nextUInt(), Random.nextBytes(32), it.key, it.value)
            }.toList())
        val issuerAuth = CoseSigned(ByteStringWrapper(CoseHeader()), null, null, byteArrayOf())
        val credential = SubjectCredentialStore.StoreEntry.Iso(
            IssuerSigned.fromIssuerSignedItems(namespacedItems, issuerAuth),
            IdAustriaScheme
        )
        val converted = CredentialToJsonConverter.toJsonElement(credential)
            .shouldBeInstanceOf<JsonObject>()
            .also { println(it) }
        val jsonMap = converted[IdAustriaScheme.isoNamespace]
            .shouldBeInstanceOf<JsonObject>()

        claims.forEach {
            withClue("Serialization for ${it.key}") {
                jsonMap[it.key].shouldNotBeNull()
            }
        }
    }
})


private fun dataMap(): Map<String, Any> =
    mapOf(
        DATE_OF_BIRTH to randomLocalDate(),
        PORTRAIT to Random.nextBytes(32),
        FIRSTNAME to Random.nextBytes(16).encodeToString(Base16())
    )

private fun randomLocalDate() = LocalDate(Random.nextInt(1900, 2100), Random.nextInt(1, 12), Random.nextInt(1, 28))

