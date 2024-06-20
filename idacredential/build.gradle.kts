import at.asitplus.gradle.serialization

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("at.asitplus.gradle.conventions")
    id("signing")
}

/* required for maven publication */
val artifactVersion: String by extra
group = "at.asitplus.wallet"
version = "$artifactVersion"

kotlin {
    jvm {
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(serialization("json"))
                api("at.asitplus.wallet:vclib:3.8.0")
            }
        }
        val commonTest by getting
        val jvmMain by getting
        val jvmTest by getting
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
}

repositories {
    mavenLocal()
    mavenCentral()
    maven(url = uri("https://s01.oss.sonatype.org/content/repositories/releases/"))
    maven(url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
}

val dokkaHtml by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class)

val javadocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    dependsOn(dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaHtml.outputDirectory)
}

publishing {
    publications {
        withType<MavenPublication> {
            artifact(javadocJar)
            pom {
                name.set("ID Austria Verifiable Credential")
                description.set("Use data provided by ID Austria as a W3C VC, or ISO 18013-5 Credential")
                url.set("https://github.com/a-sit-plus/id-austria-credential/")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("nodh")
                        name.set("Christian Kollmann")
                        email.set("christian.kollmann@a-sit.at")
                    }
                }
                scm {
                    connection.set("scm:git:git@github.com:a-sit-plus/id-austria-credential.git")
                    developerConnection.set("scm:git:git@github.com:a-sit-plus/id-austria-credential.git")
                    url.set("https://github.com/a-sit-plus/id-austria-credential/")
                }
            }
        }
    }
    repositories {
        mavenLocal {
            signing.isRequired = false
        }
    }
}

signing {
    val signingKeyId: String? by project
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
    sign(publishing.publications)
}

