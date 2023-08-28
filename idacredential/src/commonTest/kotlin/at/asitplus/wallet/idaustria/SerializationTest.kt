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
        val firstname = randomString()
        val lastname = randomString()
        val dateOfBirth = LocalDate(2023, 1, 13)
        val credential = IdAustriaCredential(
            id = id,
            firstname = firstname,
            lastname = lastname,
            dateOfBirth = dateOfBirth
        )
        val serialized = jsonSerializer.encodeToString(credential)
        println(serialized)

        val parsed: IdAustriaCredential = jsonSerializer.decodeFromString(serialized)
        parsed shouldBe credential
    }

})


val charPool = ('A'..'Z') + ('a'..'z') + ('0'..'9')

fun randomString() = (1..16)
    .map { Random.nextInt(0, charPool.size).let { charPool[it] } }
    .joinToString("")
