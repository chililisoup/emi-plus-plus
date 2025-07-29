package concerrox.emixx.config

import concerrox.emixx.EmiPlusPlus
import net.neoforged.neoforge.common.ModConfigSpec
import kotlin.io.path.div

private fun ModConfigSpec.Builder.group(path: String, action: ModConfigSpec.Builder.() -> Unit) = apply {
    push(path)
    action(this)
    pop()
}

class EmiPlusPlusConfig(builder: ModConfigSpec.Builder) {

    companion object {
        private val CONFIG_PAIR = ModConfigSpec.Builder().configure(::EmiPlusPlusConfig);
        val CONFIG_SPEC: ModConfigSpec = CONFIG_PAIR.right
        val CONFIG_DIRECTORY_PATH = EmiPlusPlus.PLATFORM.configDirectoryPath / EmiPlusPlus.MOD_ID
    }

    init {
        builder.group("stackGroups") {
            defineListAllowEmpty("disabledStackGroups", { listOf("hello") }, { "" }, { it is String })
        }
    }

}