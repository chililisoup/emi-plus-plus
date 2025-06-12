package concerrox.emixx.config

import concerrox.emixx.EmiPlusPlus
import net.neoforged.neoforge.common.ModConfigSpec
import kotlin.io.path.div


class EmiPlusPlusConfig {

    companion object {
        private val configPair = ModConfigSpec.Builder().configure(::EmiPlusPlusConfig);
        val CONFIG: EmiPlusPlusConfig = configPair.left
        val CONFIG_SPEC: ModConfigSpec = configPair.right
        val CONFIG_DIRECTORY_PATH = EmiPlusPlus.PLATFORM.configDirectoryPath / EmiPlusPlus.MOD_ID
    }

    constructor(builder: ModConfigSpec.Builder) {

    }

}