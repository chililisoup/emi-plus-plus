plugins {
    id("com.gradleup.shadow")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

loom {
    accessWidenerPath = project(":common").loom.accessWidenerPath
}

val common: Configuration by configurations.creating
val shadowCommon: Configuration by configurations.creating
val developmentFabric: Configuration by configurations.getting
configurations {
    compileOnly.configure { extendsFrom(common) }
    runtimeOnly.configure { extendsFrom(common) }
    developmentFabric.extendsFrom(common)
}

val fabricLoaderVersion: String by rootProject
val fabricLanguageKotlinVersion: String by project
val forgeConfigApiPortVersion: String by project
dependencies {
    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")
//    modImplementation("net.fabricmc:fabric-language-kotlin:$fabricLanguageKotlinVersion")
    modImplementation("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:$forgeConfigApiPortVersion")

    common(project(":common", "namedElements")) { isTransitive = false }
    shadowCommon(project(":common", "transformProductionFabric")) { isTransitive = false }
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("fabric.mod.json") {
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
