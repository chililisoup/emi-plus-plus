pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.architectury.dev/")
        gradlePluginPortal()
    }
}

rootProject.name = "emixx"

include("common")
include("fabric")
include("forge")
