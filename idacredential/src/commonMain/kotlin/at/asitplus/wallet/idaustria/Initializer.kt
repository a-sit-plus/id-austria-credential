package at.asitplus.wallet.idaustria

import at.asitplus.wallet.lib.LibraryInitializer
import at.asitplus.wallet.lib.data.CredentialSubject
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
        )
    }

}
