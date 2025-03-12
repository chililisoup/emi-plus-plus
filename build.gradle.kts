import net.fabricmc.loom.api.LoomGradleExtensionAPI

plugins {
    kotlin("jvm") version "1.8.22"
    id("architectury-plugin") version "3.4-SNAPSHOT"
    id("dev.architectury.loom") version "1.7-SNAPSHOT" apply false
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
}

architectury {
    val minecraftVersion: String by project
    minecraft = minecraftVersion
}

allprojects {
    apply(plugin = "kotlin")
    apply(plugin = "architectury-plugin")
    apply(plugin = "maven-publish")

    val mavenGroup: String by project
    val modVersion: String by project
    group = mavenGroup
    version = modVersion

    repositories {
        maven("https://maven.parchmentmc.org")
        maven("https://maven.neoforged.net/releases/")
    }

    java {
        withSourcesJar()
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    }
}

subprojects {
    apply(plugin = "dev.architectury.loom")

    base {
        val archivesName: String by project
        this.archivesName = "$archivesName-${project.name}"
    }

    val loom = project.extensions.getByName<LoomGradleExtensionAPI>("loom").apply {
        silentMojangMappingsLicense()
    }

    dependencies {
        val minecraftVersion: String by project
        "minecraft"("com.mojang:minecraft:$minecraftVersion")
        "mappings"(loom.layered {
            officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-1.20.1:2023.09.03@zip")
        })
    }
}