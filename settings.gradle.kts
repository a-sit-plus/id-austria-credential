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
