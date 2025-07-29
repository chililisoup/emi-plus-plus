package concerrox.emixx.data

import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.world.item.BannerPatternItem

internal class BannerPatternItemGroup: StackGroup("banner_patterns", StackGroup.Type.ITEM) {

    override fun match(stack: EmiIngredient): Boolean {
        if (stack !is EmiStack) return false
        return stack.itemStack.item is BannerPatternItem
    }

}