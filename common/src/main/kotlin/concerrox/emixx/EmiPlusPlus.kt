package concerrox.emixx

import com.mojang.logging.LogUtils
import concerrox.emixx.config.ContentData
import net.minecraft.resources.ResourceLocation

fun id(path: String): ResourceLocation {
    return ResourceLocation.fromNamespaceAndPath(EmiPlusPlus.MOD_ID, path)
}

object EmiPlusPlus {

    const val MOD_ID = "emixx"
    var LOGGER = LogUtils.getLogger()
    lateinit var PLATFORM: EmiPlusPlusPlatform

    fun initialize(platform: EmiPlusPlusPlatform) {
        this.PLATFORM = platform

        ContentData.doSth()
    }

    fun initializeClient() {

    }

}