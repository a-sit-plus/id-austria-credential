package at.asitplus.wallet.idaustria

import at.asitplus.wallet.lib.JsonValueEncoder
import at.asitplus.wallet.lib.LibraryInitializer
import at.asitplus.wallet.lib.data.CredentialSubject
import at.asitplus.wallet.lib.data.vckJsonSerializer
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

object Initializer {

    /**
     * A reference to this class is enough to trigger the init block
     */
    init {
        initWithVCK()
    }

    /**
     * This has to be called first, before anything first, to load the
     * relevant classes of this library into the base implementations of VC-K
     */
    fun initWithVCK() {
        LibraryInitializer.registerExtensionLibrary(
            credentialScheme = IdAustriaScheme,
            serializersModule = SerializersModule {
                polymorphic(CredentialSubject::class) {
                    subclass(IdAustriaCredential::class)
                }
            },
            jsonValueEncoder = jsonValueEncoder(),
            itemValueSerializerMap = mapOf(
                IdAustriaScheme.Attributes.DATE_OF_BIRTH to LocalDate.serializer(),
                IdAustriaScheme.Attributes.PORTRAIT to ByteArraySerializer(),
            )
        )
    }

    private fun jsonValueEncoder(): JsonValueEncoder = {
        when (it) {
            is LocalDate -> vckJsonSerializer.encodeToJsonElement(it)
            is UInt -> vckJsonSerializer.encodeToJsonElement(it)
            is Instant -> vckJsonSerializer.encodeToJsonElement(it)
            else -> null
        }
    }
}
