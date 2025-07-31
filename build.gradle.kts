plugins {
    kotlin("multiplatform") version "2.2.0" apply false
    kotlin("plugin.serialization") version "2.2.0" apply false
    id("com.google.devtools.ksp") version "2.2.0-2.0.2"
    id("io.kotest") version "6.0.0.M6"
    id("at.asitplus.gradle.conventions") version "20250728"
}

val artifactVersion: String by extra
group = "at.asitplus.wallet"
version = artifactVersion
