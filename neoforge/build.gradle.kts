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
@Suppress("UnstableApiUsage") configurations {
    compileOnly.configure { extendsFrom(common) }
    runtimeOnly.configure { extendsFrom(common) }
    developmentNeoForge.extendsFrom(common)
}

repositories {
    maven("https://thedarkcolour.github.io/KotlinForForge/")
    maven("https://modmaven.dev/")
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
    modImplementation("mekanism:Mekanism:1.21.1-10.7.0.55")

    common(project(":common", "namedElements")) { isTransitive = false }
    shadowCommon(project(":common", "transformProductionNeoForge")) { isTransitive = false }

//    modImplementation("dev.latvian.apps:tiny-java-server:1.0.0-build.26")
//    modImplementation(libs.rhino)
//    modImplementation(libs.kubejs.neoforge)
}

//tasks.register<Copy>("copyAccessWidener") {
//    val commonAw = project(":common").loom.accessWidenerPath
//    val neoforgeAw = file("src/main/resources/emixx.accesswidener")
//
//    // Create a temporary file that combines both access wideners
//    val mergedFile = temporaryDir.resolve("emixx-merged.accesswidener")
//
//    doFirst {
//        // Read and merge the content of both files, skipping the header line from the second file
//        val commonContent = commonAw.readLines()
//        val neoforgeContent = neoforgeAw.readLines()
//
//        // Keep the header from common and merge the rest
//        val mergedContent = mutableListOf<String>()
//        mergedContent.addAll(commonContent.take(1)) // Add header from common
//        mergedContent.addAll(commonContent.drop(1).filter { it.isNotBlank() })
//        mergedContent.add("") // Add a blank line between sections
//        mergedContent.add("# NeoForge")
//        mergedContent.addAll(neoforgeContent.drop(1).filter { it.isNotBlank() })
//
//        // Write the merged content to the output file
//        mergedFile.writeText(mergedContent.joinToString("\n") + "\n")
//    }
//
//    // Copy the merged file to the resources directory
//    from(mergedFile) {
//        into("")
//        rename { "emixx-merged.accesswidener" }
//    }
//    into(file("src/main/resources/"))
//}
//
//tasks.named("validateAccessWidener") {
//    dependsOn("copyAccessWidener")
//}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("META-INF/neoforge.mods.toml") {
        expand(
            mapOf(
                "version" to project.version,
            )
        )
    }
}

tasks.shadowJar {
    configurations = listOf(shadowCommon)
    archiveClassifier.set("dev-shadow")
}

tasks.remapJar {
    inputFile.set(tasks.shadowJar.get().archiveFile)
    atAccessWideners.add("emixx-common.accesswidener")
//    atAccessWideners.add("emixx.accesswidener")
}