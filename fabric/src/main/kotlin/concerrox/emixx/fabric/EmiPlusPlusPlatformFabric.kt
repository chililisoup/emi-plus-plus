package concerrox.emixx.fabric

import concerrox.emixx.EmiPlusPlusPlatform
import net.fabricmc.loader.api.FabricLoader
import java.nio.file.Path

object EmiPlusPlusPlatformFabric : EmiPlusPlusPlatform {

    override val configDirectoryPath: Path = FabricLoader.getInstance().configDir

}