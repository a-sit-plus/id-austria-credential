import at.asitplus.gradle.*

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
                api("at.asitplus.wallet:vclib-openid:3.0.2")
            }
        }
        val commonTest by getting
        val jvmMain by getting
        val jvmTest by getting
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://s01.oss.sonatype.org/content/repositories/releases/")
    }
    maven{
        url = uri("https://raw.githubusercontent.com/a-sit-plus/kotlinx.serialization/mvn/repo")
    }
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
                name.set("W3C VC ID Austria Credential")
                description.set("Use data provided by ID Austria as a W3C VC")
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

