plugins {
    kotlin("multiplatform") version "1.8.20"
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
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("io.github.aakira:napier:2.6.1")
                implementation("io.ktor:ktor-http:2.2.1")
                implementation("io.ktor:ktor-utils:2.2.1")
                api("at.asitplus.wallet:oidvci-library:1.0.0-SNAPSHOT")
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