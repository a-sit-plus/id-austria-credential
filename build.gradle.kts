plugins {
    kotlin("multiplatform") version "1.8.20"
    kotlin("plugin.serialization") version "1.8.20"
    id("io.kotest.multiplatform") version "5.5.4"
    id("maven-publish")
}

group = "at.asitplus.wallet"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.github.aakira:napier:2.6.1")
                implementation("io.ktor:ktor-http:2.2.1")
                implementation("io.ktor:ktor-utils:2.2.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                api("at.asitplus.wallet:vclib-openid:2.0.0-SNAPSHOT")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation("io.kotest:kotest-assertions-core:5.5.4")
                implementation("io.kotest:kotest-framework-engine:5.5.4")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.kotest:kotest-runner-junit5-jvm:5.5.4")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime-jvm:0.4.0")
            }
        }
        val jvmTest by getting
    }
}

publishing {
    repositories {
        mavenLocal()
    }
}