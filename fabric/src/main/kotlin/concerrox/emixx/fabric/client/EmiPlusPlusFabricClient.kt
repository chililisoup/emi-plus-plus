package concerrox.emixx.fabric.client

import concerrox.emixx.EmiPlusPlus
import net.fabricmc.api.ClientModInitializer

class EmiPlusPlusFabricClient : ClientModInitializer {

    override fun onInitializeClient() {
        EmiPlusPlus.initializeClient()
    }

}