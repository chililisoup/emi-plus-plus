plugins {
    id("com.github.johnrengelman.shadow")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

val common: Configuration by configurations.creating
val shadowCommon: Configuration by configurations.creating
configurations {
    compileOnly.configure { extendsFrom(common) }
    runtimeOnly.configure { extendsFrom(common) }
    val developmentFabric: Configuration by configurations.getting
    developmentFabric.extendsFrom(common)
}

dependencies {
    val fabricLoaderVersion: String by project
    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")

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
