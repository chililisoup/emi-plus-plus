package concerrox.emixx.forge

import concerrox.emixx.EmiPlusPlus
import net.minecraftforge.fml.common.Mod

@Mod(EmiPlusPlus.MOD_ID)
object EmiPlusPlusForge {

    init {
        EmiPlusPlus.initialize()
    }

}