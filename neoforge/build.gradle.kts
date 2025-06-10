plugins {
    id("com.gradleup.shadow")
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

loom {
    accessWidenerPath = project(":common").loom.accessWidenerPath
}

val common: Configuration by configurations.creating
val shadowCommon: Configuration by configurations.creating
val developmentNeoForge: Configuration by configurations.getting
configurations {
    compileOnly.configure { extendsFrom(common) }
    runtimeOnly.configure { extendsFrom(common) }
    developmentNeoForge.extendsFrom(common)
}

repositories {
    maven("https://thedarkcolour.github.io/KotlinForForge/")
}

val neoForgeVersion: String by project
val kotlinForForgeVersion: String by project
val emiVersion: String by project
dependencies {
    neoForge("net.neoforged:neoforge:$neoForgeVersion")
    implementation("thedarkcolour:kotlinforforge-neoforge:$kotlinForForgeVersion") {
        exclude(group = "net.neoforged.fancymodloader", module = "loader")
    }
    modImplementation("dev.emi:emi-neoforge:$emiVersion")

    common(project(":common", "namedElements")) { isTransitive = false }
    shadowCommon(project(":common", "transformProductionNeoForge")) { isTransitive = false }
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("META-INF/neoforge.mods.toml") {
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
