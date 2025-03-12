architectury {
    val enabledPlatforms: String by project
    common(enabledPlatforms.split(","))
}

dependencies {
    // We depend on Fabric Loader here to use the Fabric @Environment annotations,
    // which get remapped to the correct annotations on each platform.
    // Do NOT use other classes from Fabric Loader.
    val fabricLoaderVersion: String by project
    modImplementation("net.fabricmc:fabric-loader:$fabricLoaderVersion")
    val emiVersion: String by project
    modCompileOnly("dev.emi:emi-xplat-intermediary:$emiVersion")
}
