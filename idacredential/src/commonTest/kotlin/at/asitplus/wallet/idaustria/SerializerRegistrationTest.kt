package at.asitplus.wallet.idaustria

import at.asitplus.signum.indispensable.CryptoSignature
import at.asitplus.signum.indispensable.cosef.*
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
import at.asitplus.wallet.lib.iso.*
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.FreeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.JsonObject
import kotlin.random.Random
import kotlin.random.nextUInt

class SerializerRegistrationTest : FreeSpec({

    "Serialization and deserialization" - {
        withData(nameFn = { "for ${it.key}" }, dataMap().entries) {
            val item = it.toIssuerSignedItem()
            val serialized = item.serialize(IdAustriaScheme.isoNamespace)

            val deserialized =
                IssuerSignedItem.deserialize(serialized, IdAustriaScheme.isoNamespace, item.elementIdentifier)
                    .getOrThrow()

            deserialized.elementValue shouldBe it.value
        }
    }

    "Serialization to JSON Element" {
        val claims = dataMap()
        val namespacedItems = mapOf(IdAustriaScheme.isoNamespace to claims.map { it.toIssuerSignedItem() }.toList())
        val mso = MobileSecurityObject(
            version = "1.0",
            digestAlgorithm = "SHA-256",
            valueDigests = mapOf("foo" to ValueDigestList(listOf(ValueDigest(0U, byteArrayOf())))),
            deviceKeyInfo = deviceKeyInfo(),
            docType = "docType",
            validityInfo = ValidityInfo(Clock.System.now(), Clock.System.now(), Clock.System.now())
        )
        val issuerAuth: CoseSigned<MobileSecurityObject> = CoseSigned.create(
            CoseHeader(),
            null,
            mso,
            CryptoSignature.RSAorHMAC(rawBytes = byteArrayOf(1, 3, 3, 7)),
            MobileSecurityObject.serializer()
        )
        val credential = SubjectCredentialStore.StoreEntry.Iso(
            IssuerSigned.fromIssuerSignedItems(namespacedItems, issuerAuth),
            IdAustriaScheme.isoNamespace
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

private fun deviceKeyInfo() =
    DeviceKeyInfo(CoseKey(CoseKeyType.EC2, keyParams = CoseKeyParams.EcYBoolParams(CoseEllipticCurve.P256)))

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

