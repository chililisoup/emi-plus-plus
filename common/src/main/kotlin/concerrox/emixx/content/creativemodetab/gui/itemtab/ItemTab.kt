package concerrox.emixx.content.creativemodetab.gui.itemtab

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.components.tabs.Tab
import net.minecraft.client.gui.navigation.ScreenRectangle
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import java.util.function.Consumer

@Environment(EnvType.CLIENT)
class ItemTab(val creativeModeTab: CreativeModeTab?) : Tab {
    override fun getTabTitle(): Component {
        return creativeModeTab?.displayName ?: Component.empty()
    }
    override fun visitChildren(consumer: Consumer<AbstractWidget>) {}
    override fun doLayout(rectangle: ScreenRectangle) {

    }
}