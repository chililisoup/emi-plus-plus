plugins {
    id("com.github.johnrengelman.shadow")
}

architectury {
    platformSetupLoomIde()
    forge()
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)

    forge.apply {
//        convertAccessWideners.set(true)
//        extraAccessWideners.add(loom.accessWidenerPath.get().asFile.name)
        mixinConfig("emixx-common.mixins.json")
//        mixinConfig("emixx.mixins.json")
    }
}

val common: Configuration by configurations.creating
val shadowCommon: Configuration by configurations.creating
configurations {
    compileOnly.configure { extendsFrom(common) }
    runtimeOnly.configure { extendsFrom(common) }
    val developmentForge: Configuration by configurations.getting
    developmentForge.extendsFrom(common)
}

repositories {
    maven("https://thedarkcolour.github.io/KotlinForForge/")
}

dependencies {
    val forgeVersion: String by project
    forge("net.minecraftforge:forge:$forgeVersion")
    val kotlinForForgeVersion: String by project
    implementation("thedarkcolour:kotlinforforge:$kotlinForForgeVersion")

    common(project(":common", "namedElements")) { isTransitive = false }
    shadowCommon(project(":common", "transformProductionForge")) { isTransitive = false }
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("META-INF/mods.toml") {
        expand(mapOf(
            "version" to project.version,
        ))
    }
}

tasks.shadowJar {
    configurations = listOf(shadowCommon)
    archiveClassifier.set("dev-shadow")
}

tasks.remapJar {
    inputFile.set(tasks.shadowJar.get().archiveFile)
}
