package concerrox.emixx.neoforge

import concerrox.emixx.EmiPlusPlus
import concerrox.emixx.config.EmiPlusPlusConfig
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig

@Mod(EmiPlusPlus.MOD_ID)
class EmiPlusPlusNeoForge(eventBus: IEventBus, container: ModContainer) {

    init {
        EmiPlusPlus.initialize(EmiPlusPlusPlatformNeoForge)
        container.registerConfig(ModConfig.Type.CLIENT, EmiPlusPlusConfig.CONFIG_SPEC, "emixx/emixx-client.toml")
    }

}