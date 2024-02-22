pluginManagement {
    repositories {
        maven {
            url = uri("https://raw.githubusercontent.com/a-sit-plus/gradle-conventions-plugin/mvn/repo")
            name = "aspConventions"
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "idaustriacredential"
include(":idacredential")

//workaround IDEA bug
includeBuild("kmm-vc-library/kmp-crypto"){
    dependencySubstitution {
        substitute(module("at.asitplus.crypto:datatypes")).using(project(":datatypes"))
        substitute(module("at.asitplus.crypto:datatypes-jws")).using(project(":datatypes-jws"))
        substitute(module("at.asitplus.crypto:datatypes-cose")).using(project(":datatypes-cose"))
    }
}

includeBuild("kmm-vc-library"){
    dependencySubstitution {
        substitute(module("at.asitplus.wallet:vclib")).using(project(":vclib"))
        substitute(module("at.asitplus.wallet:vclib-aries")).using(project(":vclib-aries"))
        substitute(module("at.asitplus.wallet:vclib-openid")).using(project(":vclib-openid"))
    }
}
