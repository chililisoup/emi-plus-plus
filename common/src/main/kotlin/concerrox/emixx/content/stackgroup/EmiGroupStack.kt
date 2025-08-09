package concerrox.emixx.content.stackgroup

import concerrox.emixx.content.ScreenManager.ENTRY_SIZE
import concerrox.emixx.content.stackgroup.data.StackGroup
import concerrox.emixx.text
import concerrox.emixx.util.GuiGraphicsUtils
import concerrox.emixx.util.push
import dev.emi.emi.EmiPort
import dev.emi.emi.EmiRenderHelper
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.runtime.EmiDrawContext
import net.minecraft.ChatFormatting
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent
import net.minecraft.core.component.DataComponentPatch
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import java.util.*

class EmiGroupStack(val group: StackGroup) : EmiStack() {

    var isExpanded = false

    @Deprecated("")
    internal val items = mutableListOf<GroupedEmiStack<EmiStack>>()

    override fun isEmpty() = true
    override fun getKey() = group

    @Deprecated("")
    override fun getId() = group.id

    override fun equals(other: Any?) = this === other
    override fun toString() = key.toString()
    override fun getTooltip() = tooltipText.map { ClientTooltipComponent.create(it.visualOrderText) }
    override fun getComponentChanges(): DataComponentPatch = DataComponentPatch.EMPTY

    override fun render(raw: GuiGraphics, x: Int, y: Int, delta: Float, flags: Int) {
        EmiDrawContext.wrap(raw).push {
            if (isExpanded) {
                fill(x - 1, y - 1, 1, ENTRY_SIZE, 0xFFFFFFFF.toInt())
                fill(x - 1, y - 1, ENTRY_SIZE, 1, 0xFFFFFFFF.toInt())
                fill(x + ENTRY_SIZE - 2, y - 1, 1, ENTRY_SIZE, 0xFFFFFFFF.toInt())
                fill(x - 1, y + ENTRY_SIZE - 2, ENTRY_SIZE, 1, 0xFFFFFFFF.toInt())
                fill(x, y, ENTRY_SIZE - 2, ENTRY_SIZE - 2, 0x30FFFFFF)
            }

            if (items.size >= 3) GuiGraphicsUtils.renderItem(raw, items[2].itemStack, x + 3.5F, y + 0.5F, 12F)
            matrices().translate(0F, 0F, 10F)
            if (items.size >= 2) GuiGraphicsUtils.renderItem(raw, items[1].itemStack, x + 2F, y + 2F, 12F)
            matrices().translate(0F, 0F, 10F)
            if (items.size >= 1) GuiGraphicsUtils.renderItem(raw, items[0].itemStack, x + 0.5F, y + 3.5F, 12F)
            EmiRenderHelper.renderAmount(this, x, y, EmiPort.literal(if (isExpanded) "-" else "+"))
        }
    }

    override fun copy() = EmiGroupStack(group).apply {
        isExpanded = this@EmiGroupStack.isExpanded
        items.addAll(this@EmiGroupStack.items)
    }

    override fun getName(): MutableComponent {
        return Component.translatableWithFallback("stackgroup.emixx.${group.id}", buildStackDefaultName())
    }

    override fun getTooltipText(): MutableList<Component> {
        return mutableListOf(name, text("stackgroup", "tooltip").withStyle(ChatFormatting.DARK_GRAY))
    }

    private fun buildStackDefaultName(): String {
        return group.id.path.split("_").joinToString(" ") { it.replaceFirstChar { c -> c.titlecase(Locale.getDefault()) } }
    }

    override fun hashCode(): Int {
        var result = group.hashCode()
        result = 31 * result + isExpanded.hashCode()
        result = 31 * result + items.hashCode()
        return result
    }

}