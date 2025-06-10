import net.fabricmc.loom.api.LoomGradleExtensionAPI

plugins {
    kotlin("jvm") version "2.0.21"
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("dev.architectury.loom") version "1.7-SNAPSHOT" apply false
    id("com.gradleup.shadow") version "8.3.6" apply false
}

val minecraftVersion: String by project
architectury {
    minecraft = minecraftVersion
}

val mavenGroup: String by project
val modVersion: String by project
allprojects {
    group = mavenGroup
    version = modVersion
}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "architectury-plugin")
    apply(plugin = "dev.architectury.loom")

    val loom = project.extensions.getByName<LoomGradleExtensionAPI>("loom").apply {
        silentMojangMappingsLicense()
    }

    val archivesName: String by project
    base {
        this.archivesName = "$archivesName-${project.name}"
    }

    repositories {
        maven("https://maven.parchmentmc.org")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.terraformersmc.com/")
    }

    dependencies {
        val minecraftVersion: String by project
        "minecraft"("com.mojang:minecraft:$minecraftVersion")
        @Suppress("UnstableApiUsage") "mappings"(loom.layered {
            officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-1.21.1:2024.11.17@zip")
        })
    }

}