package at.asitplus.wallet.idaustria

import io.kotest.core.config.AbstractProjectConfig

class KotestConfig : AbstractProjectConfig() {
    init {
        Initializer.initWithVCK()
    }
}