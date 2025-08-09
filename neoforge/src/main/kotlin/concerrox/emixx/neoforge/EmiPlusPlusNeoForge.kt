package concerrox.emixx.neoforge

import concerrox.emixx.EmiPlusPlus
import concerrox.emixx.content.villagertrade.VillagerTradeManager
import dev.emi.emi.api.stack.EmiStack
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.common.BasicItemListing

@Mod(EmiPlusPlus.MOD_ID)
class EmiPlusPlusNeoForge(eventBus: IEventBus, container: ModContainer) {

    init {
        EmiPlusPlus.initialize(EmiPlusPlusPlatformNeoForge)

        VillagerTradeManager.addCustomVillagerTradeType(BasicItemListing::class.java) { itemListing ->
            val inputs = mutableListOf(EmiStack.of(itemListing.price), EmiStack.of(itemListing.price2))
            val output = mutableListOf(EmiStack.of(itemListing.forSale))
            inputs to output
        }
    }

}