package at.asitplus.wallet.idaustria

import at.asitplus.wallet.lib.data.jsonSerializer
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDate
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.random.Random

class SerializationTest : FunSpec({

    test("serialize credential") {
        Initializer.initWithVcLib()

        val id = randomString()
        val bpk = BpkIntermediateValues(randomString().toByteArray(), randomString().toByteArray())
        val firstname = randomString()
        val lastname = randomString()
        val dateOfBirth = LocalDate(2023, 1, 13)
        val portrait = randomString().toByteArray()
        val credential = IdAustriaCredential(
            id = id,
            bpkIntermediates = bpk,
            firstname = firstname,
            lastname = lastname,
            dateOfBirth = dateOfBirth,
            portrait = portrait,
        )
        val serialized = jsonSerializer.encodeToString(credential)
        println(serialized)

        val parsed: IdAustriaCredential = jsonSerializer.decodeFromString(serialized)
        parsed shouldBe credential
    }

    test("deserialize credential") {
        val serialCred = "{" +
                "\"id\":\"Test ID\"," +
                "\"bpk-intermediates\":\"{" +
                    "\\\"blinded-bkz\\\":\\\"VGVzdCBCS1o\\\"," +
                    "\\\"blinded-bpk\\\":\\\"VGVzdCBCUEs\\\"" +
                "}\"," +
                "\"firstname\":\"Ha\\u010dek\"," +
                "\"lastname\":\"Musterfrau\"," +
                "\"date-of-birth\":\"1901-02-03\"," +
                "\"portrait\":\"dGhpcy1pcy1hLWZhY2U\"" +
            "}"

        val cred: IdAustriaCredential = jsonSerializer.decodeFromString(serialCred)
        cred.id shouldBe "Test ID"
        cred.bpkIntermediates.blindedBKZ shouldBe "Test BKZ".toByteArray()
        cred.bpkIntermediates.blindedBPK shouldBe "Test BPK".toByteArray()
        cred.firstname shouldBe "Haček"
        cred.lastname shouldBe "Musterfrau"
        cred.dateOfBirth shouldBe LocalDate(year=1901, monthNumber=2, dayOfMonth=3)
        cred.portrait shouldBe "this-is-a-face".toByteArray()
    }

})


val charPool = ('A'..'Z') + ('a'..'z') + ('0'..'9')

fun randomString() = (1..16)
    .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
    .joinToString("")
