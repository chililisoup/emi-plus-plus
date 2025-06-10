package concerrox.emixx

import concerrox.emixx.mixin.EmiScreenManagerAccessor
import dev.emi.emi.api.widget.Bounds
import dev.emi.emi.config.SidebarType
import dev.emi.emi.screen.EmiScreenManager
import dev.emi.emi.screen.widget.SizedButtonWidget
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen


object EmiPlusPlusScreenManager {

    const val TILE_SIZE = 18

    private var screen: AbstractContainerScreen<*>? = null

//    private fun <T> addRenderableWidget(widget: T) where T : GuiEventListener, T : Renderable, T : NarratableEntry {
//        screen?.addRenderableWidget(widget)
//    }
//
//    fun removeWidget(listener: GuiEventListener) {
//        screen?.removeWidget(listener)
//    }

    fun onScreenInitialized(screen: AbstractContainerScreen<*>) {
        this.screen = screen
    }

    fun onEmiPanelsCreated() {
        getIndexPanelSpace()?.let {
            val size = it.tw - 2
//            val tabs = BuiltInRegistries.CREATIVE_MODE_TAB.map(::CreativeModeTab).slice(0 until size).toTypedArray()
            val pageLeft = SizedButtonWidget(it.tx, 6, 16, 16, 224, 0, { true }, { })
            val pageRight = SizedButtonWidget(it.tx + TILE_SIZE + (size * TILE_SIZE) + 2, 6, 16, 16, 240, 0, { true }, { })
            screen?.addRenderableWidget(pageLeft)
            screen?.addRenderableWidget(pageRight)

        }
    }

    fun tick() {
//        creativeModeTabManager.()
    }

    fun getExclusionAreas(original: ArrayList<Bounds>): ArrayList<Bounds> {
//        original.add(Bounds(0, 0, 100, 100))
        return original
    }

    private fun getIndexPanelSpace(): EmiScreenManager.ScreenSpace? {
        return EmiScreenManagerAccessor.getPanels().firstOrNull { panel ->
            panel.pages.pages.any { it.type == SidebarType.INDEX }
        }?.space
    }

}