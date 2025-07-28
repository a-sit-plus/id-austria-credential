package at.asitplus.wallet.idaustria

import at.asitplus.wallet.lib.data.vckJsonSerializer
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.datetime.LocalDate
import kotlin.random.Random

class SerializationTest : FunSpec({

    test("serialize credential") {
        val id = randomString()
        val firstname = randomString()
        val lastname = randomString()
        val bpk = randomString()
        val dateOfBirth = LocalDate(2023, 1, 13)
        val portrait = Random.nextBytes(32)
        val credential = IdAustriaCredential(
            id = id,
            bpk = bpk,
            firstname = firstname,
            lastname = lastname,
            dateOfBirth = dateOfBirth,
            portrait = portrait,
        )
        val serialized = vckJsonSerializer.encodeToString(credential)

        val parsed: IdAustriaCredential = vckJsonSerializer.decodeFromString(serialized)
        parsed shouldBe credential
    }

    test("deserialize credential") {
        val serialized = "{" +
                "\"id\":\"Test ID\"," +
                "\"bpk\":\"Test BPK\"," +
                "\"firstname\":\"Ha\\u010dek\"," +
                "\"lastname\":\"Musterfrau\"," +
                "\"date-of-birth\":\"1901-02-03\"," +
                "\"portrait\":\"dGhpcy1pcy1hLWZhY2U\"" +
            "}"

        val parsed: IdAustriaCredential = vckJsonSerializer.decodeFromString(serialized)

        parsed.id shouldBe "Test ID"
        parsed.bpk shouldBe "Test BPK"
        parsed.firstname shouldBe "Haček"
        parsed.lastname shouldBe "Musterfrau"
        parsed.dateOfBirth shouldBe LocalDate(year=1901, monthNumber=2, dayOfMonth=3)
        parsed.portrait shouldBe "this-is-a-face".encodeToByteArray()
    }

})


val charPool = ('A'..'Z') + ('a'..'z') + ('0'..'9')

fun randomString() = (1..16)
    .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
    .joinToString("")
