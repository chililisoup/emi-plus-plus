package concerrox.emixx

import concerrox.emixx.content.villagertrade.VillagerTradeManager
import dev.emi.emi.api.EmiEntrypoint
import dev.emi.emi.api.EmiPlugin
import dev.emi.emi.api.EmiRegistry

@EmiEntrypoint
class EmiPlusPlusPlugin : EmiPlugin {

    override fun register(registry: EmiRegistry) {
        VillagerTradeManager.initialize(registry)
    }

}