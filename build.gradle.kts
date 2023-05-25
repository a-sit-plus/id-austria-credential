plugins {
    kotlin("multiplatform") version "1.8.20"
    kotlin("plugin.serialization") version "1.8.20"
    id("io.kotest.multiplatform") version "5.5.4"
    id("maven-publish")
}

/* required for maven publication */
val artifactVersion: String by extra
group = "at.asitplus.wallet"
version = "$artifactVersion"

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

val gitLabPrivateToken: String? by extra
val gitLabProjectId: String by extra
val gitLabGroupId: String by extra

repositories {
    mavenLocal()
    if (System.getenv("CI_JOB_TOKEN") != null || gitLabPrivateToken != null) {
        maven {
            name = "gitlab"
            url = uri("https://gitlab.iaik.tugraz.at/api/v4/groups/$gitLabGroupId/-/packages/maven")
            if (gitLabPrivateToken != null) {
                credentials(HttpHeaderCredentials::class) {
                    name = "Private-Token"
                    value = gitLabPrivateToken
                }
            } else if (System.getenv("CI_JOB_TOKEN") != null) {
                credentials(HttpHeaderCredentials::class) {
                    name = "Job-Token"
                    value = System.getenv("CI_JOB_TOKEN")
                }
            }
            authentication {
                create<HttpHeaderAuthentication>("header")
            }
        }
    }
    mavenCentral()
    maven {
        url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    }
}


publishing {
    repositories {
        mavenLocal()
        if (System.getenv("CI_JOB_TOKEN") != null) {
            maven {
                name = "gitlab"
                url = uri("https://gitlab.iaik.tugraz.at/api/v4/projects/$gitLabProjectId/packages/maven")
                credentials(HttpHeaderCredentials::class) {
                    name = "Job-Token"
                    value = System.getenv("CI_JOB_TOKEN")
                }
                authentication {
                    create<HttpHeaderAuthentication>("header")
                }
            }
        }
    }
}
