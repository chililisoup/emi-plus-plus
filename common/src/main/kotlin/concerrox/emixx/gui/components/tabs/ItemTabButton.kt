package concerrox.emixx.gui.components.tabs

import com.mojang.blaze3d.systems.RenderSystem
import concerrox.emixx.gui.GuiGraphicsUtils
import concerrox.emixx.res
import dev.emi.emi.runtime.EmiDrawContext
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.TabButton

class ItemTabButton(private val tabManager: ItemTabManager, private val tab: ItemTab, width: Int, height: Int) :
    TabButton(tabManager, tab, width, height) {

    companion object {
        private val TEXTURE = res("textures/gui/buttons.png")
    }

    override fun onClick(mouseX: Double, mouseY: Double) {
        super.onClick(mouseX, mouseY)
        tabManager.onTabSelected(tab)
    }

    override fun renderWidget(raw: GuiGraphics, mouseX: Int, mouseY: Int, partialTick: Float) {
        RenderSystem.enableBlend()
        val context = EmiDrawContext.wrap(raw)
        if (isSelected) {
            context.drawTexture(TEXTURE, x, y, 32, if (isHoveredOrFocused) 50 else 32, width, 18)
            renderMenuBackground(raw, x + 2, y + 2, right - 2, bottom)
        } else {
            context.drawTexture(TEXTURE, x, y + 2, 32, if (isHoveredOrFocused) 16 else 0, width, 16)
        }
        RenderSystem.disableBlend()
        GuiGraphicsUtils.renderItem(raw, tab.creativeModeTab?.iconItem, x + 4F, y + 5F, 10F)
    }

}