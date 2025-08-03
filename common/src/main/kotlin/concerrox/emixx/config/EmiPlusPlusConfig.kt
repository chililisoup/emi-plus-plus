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

        fun save() {
            CONFIG_SPEC.save()
        }

        lateinit var enableCreativeModeTabs: ModConfigSpec.BooleanValue
        lateinit var syncSelectedCreativeModeTab: ModConfigSpec.BooleanValue
        lateinit var enableStackGroups: ModConfigSpec.BooleanValue
        lateinit var disabledStackGroups: ModConfigSpec.ConfigValue<List<String>>
    }

    init {
        builder.group("creativeModeTabs") {
            enableCreativeModeTabs = define("enableCreativeModeTabs", true)
            syncSelectedCreativeModeTab = define("syncSelectedCreativeModeTab", true)
        }
        builder.group("stackGroups") {
            enableStackGroups = define("enableStackGroups", true)
            disabledStackGroups = defineListAllowEmpty("disabledStackGroups", {
                listOf(
                    "hello",
                    "shovels",
                    "pickaxes",
                    "axes",
                    "head_armors",
                    "hoes",
                    "swords",
                    "chest_armors",
                    "leg_armors",
                    "foot_armors",
                    "raw_materials",
                    "infested_blocks",
                    "animal_armors",
                    "foods",
                    "slabs",
                    "doors",
                    "trapdoors",
                    "fences",
                    "planks",
                    "stairs",
                    "fence_gates",
                    "pressure_plates",
                    "rails",
                    "saplings",
                    "buttons",
                    "skulls",
                    "minecarts",
                    "dyes",
                    "ores",
                    "leaves",
                    "signs",
                    "seeds",
                    "logs",
                    "hanging_signs",
                    "glass_blocks",
                    "flowers",
                    "wools",
                    "walls",
                    "boats",
                    "glass_panes",
                    "terracottas",
                    "banners",
                    "wool_carpets",
                    "shulker_boxes",
                    "glazed_terracottas",
                    "concrete_powders",
                    "concretes",
                    "beds",
                    "candles",
                    "buckets",
                    "copper_blocks"
                )
            }, { "" }, { it is String })
        }
    }

}