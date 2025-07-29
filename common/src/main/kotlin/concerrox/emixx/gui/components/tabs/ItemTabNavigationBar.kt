package concerrox.emixx.gui.components.tabs

import com.google.common.collect.ImmutableList
import com.mojang.blaze3d.systems.RenderSystem
import concerrox.emixx.gui.EmiPlusPlusScreenManager
import concerrox.emixx.res
import dev.emi.emi.runtime.EmiDrawContext
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.tabs.TabNavigationBar
import net.minecraft.client.gui.layouts.LinearLayout

class ItemTabNavigationBar(private val tabManager: ItemTabManager) :
    TabNavigationBar(0, tabManager, arrayListOf()) {

    var x = 0
    var y = 0

    fun setTabs(tabs: MutableList<ItemTab>) {
        this.tabs = ImmutableList.copyOf(tabs)
        // Replace the original layout so that we can customize the tab buttons
        layout = LinearLayout.horizontal()
        tabButtons = ImmutableList.copyOf(tabs.map {
            layout.addChild(ItemTabButton(tabManager, it, 0, EmiPlusPlusScreenManager.TILE_SIZE))
        })
    }

    override fun render(raw: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        RenderSystem.enableBlend()
        EmiDrawContext.wrap(raw).apply {
            drawTexture(TEXTURE, x, y + 2, 32, 0, 1, 16)
            drawTexture(TEXTURE, x + 1, y + 2, 32, 0, 1, 16)
            drawTexture(TEXTURE, x + width - 1, y + 2, 32, 0, 1, 16)
            drawTexture(TEXTURE, x + width - 2, y + 2, 32, 0, 1, 16)
        }
        RenderSystem.disableBlend()

        tabButtons.forEach {
            it.render(raw, mouseX, mouseY, partialTick)
        }
    }

    override fun arrangeElements() {
        tabButtons.forEach {
            it.width = EmiPlusPlusScreenManager.TILE_SIZE
        }
        layout.arrangeElements()
        layout.x = x + 2
        layout.y = y
    }

    companion object {
        private val TEXTURE = res("textures/gui/buttons.png")
    }

}