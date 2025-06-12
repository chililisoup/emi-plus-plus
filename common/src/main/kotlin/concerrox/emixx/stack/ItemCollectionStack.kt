package concerrox.emixx.stack

import concerrox.emixx.gui.GuiGraphicsUtils
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.runtime.EmiDrawContext
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Items

class ItemCollectionStack : EmiStack() {

    var isExpanded = false
    var items = listOf<EmiStack>(of(Items.OAK_PLANKS), of(Items.ACACIA_PLANKS), of(Items.DARK_OAK_PLANKS),
        of(Items.SPRUCE_PLANKS), of(Items.CHERRY_PLANKS), of(Items.BIRCH_PLANKS), of(Items.JUNGLE_PLANKS),
        of(Items.CRIMSON_PLANKS), of(Items.WARPED_PLANKS))

    override fun render(raw: GuiGraphics, x: Int, y: Int, delta: Float, flags: Int) {
        EmiDrawContext.wrap(raw).apply {
            push()
            matrices().translate(0F, 0F, 50F)
            GuiGraphicsUtils.renderItem(raw, items[2].itemStack, x + 3.5F, y + 0.5F, 12F)
            matrices().translate(0F, 0F, 51F)
            GuiGraphicsUtils.renderItem(raw, items[1].itemStack, x + 2F, y + 2F, 12F)
            matrices().translate(0F, 0F, 52F)
            GuiGraphicsUtils.renderItem(raw, items[0].itemStack, x + 0.5F, y + 3.5F, 12F)
            pop()
        }
    }

    override fun toString(): String {
        return "It sucks"
    }

    override fun equals(other: Any?): Boolean {
        return this === other
    }

    override fun isEmpty(): Boolean {
        return true
    }

    override fun copy(): EmiStack {
        TODO("Not yet implemented")
    }

    override fun getComponentChanges(): DataComponentPatch {
        TODO("Not yet implemented")
    }

    override fun getKey(): Any {
        TODO("Not yet implemented")
    }

    override fun getId(): ResourceLocation {
        TODO("Not yet implemented")
    }

    override fun getTooltipText(): MutableList<Component> {
        TODO("Not yet implemented")
    }

    override fun getName(): Component {
        TODO("Not yet implemented")
    }
}