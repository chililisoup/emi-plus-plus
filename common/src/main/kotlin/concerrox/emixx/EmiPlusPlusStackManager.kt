package concerrox.emixx

import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.registry.EmiStackList
import dev.emi.emi.screen.EmiScreenManager
import dev.emi.emi.search.EmiSearch

object EmiPlusPlusStackManager {

    var sourceStacks: MutableList<EmiIngredient> = EmiStackList.filteredStacks.toMutableList()
    var displayedStacks = mutableListOf<EmiIngredient>()

    fun updateStacks(stacks: List<EmiIngredient>) {
        sourceStacks = stacks.toMutableList()
        displayedStacks = stacks.toMutableList()
        if (EmiScreenManager.search.message.toString().isNotEmpty()) EmiSearch.update()
        EmiSearch.stacks = stacks
        EmiScreenManager.recalculate()
    }

}