package concerrox.emixx.data

import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.world.item.AnimalArmorItem

internal class AnimalArmorItemGroup: StackGroup("animal_armors", StackGroup.Type.ITEM) {

    override fun match(stack: EmiIngredient): Boolean {
        if (stack !is EmiStack) return false
        return stack.itemStack.item is AnimalArmorItem
    }

}