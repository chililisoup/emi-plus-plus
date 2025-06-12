package concerrox.emixx.fabric

import concerrox.emixx.EmiPlusPlus
import concerrox.emixx.EmiPlusPlusPlatform
import net.fabricmc.api.ModInitializer
import java.nio.file.Path

class EmiPlusPlusFabric : ModInitializer {

    override fun onInitialize() {
        EmiPlusPlus.initialize(object : EmiPlusPlusPlatform {

            override val configDirectoryPath: Path
                get() = TODO("Not yet implemented")
        })
    }

}
