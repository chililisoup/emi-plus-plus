val enabledPlatforms: String by rootProject
architectury {
    common(enabledPlatforms.split(","))
}

val emiVersion: String by rootProject
dependencies {
    modCompileOnly("dev.emi:emi-xplat-intermediary:$emiVersion")
}
