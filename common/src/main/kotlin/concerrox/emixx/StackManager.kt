package concerrox.emixx

import concerrox.emixx.content.stackgroup.StackGroupManager
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.registry.EmiStackList
import dev.emi.emi.screen.EmiScreenManager
import dev.emi.emi.search.EmiSearch

object StackManager {

    var sourceStacks: MutableList<EmiIngredient> = EmiStackList.filteredStacks.toMutableList()

    fun onEmiIndexSearch(
        query: String, searchedStacks: List<EmiIngredient>
    ): List<EmiIngredient> {
        return buildDisplayedStacks(searchedStacks)
    }

    private fun buildDisplayedStacks(source: List<EmiIngredient>): List<EmiIngredient> {
        return StackGroupManager.buildGroupedStacks(mutableListOf<EmiIngredient>().apply {
            addAll(source)
        })
    }

    fun updateStacks(stacks: List<EmiIngredient>) {
        EmiSearch.stacks = StackGroupManager.buildGroupedStacks(sourceStacks)
//        EmiSearch.stacks = StackGroupManager.groupedStacks
        EmiScreenManager.recalculate()
    }


}