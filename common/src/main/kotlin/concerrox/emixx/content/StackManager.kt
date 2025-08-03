package concerrox.emixx.content

import concerrox.emixx.EmiPlusPlus
import concerrox.emixx.content.stackgroup.EmiGroupStack
import concerrox.emixx.content.stackgroup.StackGroupManager
import dev.emi.emi.api.stack.EmiIngredient
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

    fun updateStacks() {
        EmiSearch.stacks = StackGroupManager.buildGroupedStacks(sourceStacks)
//        EmiSearch.stacks = StackGroupManager.groupedStacks
        EmiScreenManager.recalculate()
    }

    fun onStackInteraction(ingredient: EmiIngredient) {
        EmiPlusPlus.LOGGER.info("onStackInteraction: $ingredient")
        when (ingredient) {
            is EmiGroupStack -> {
                val stacks = EmiSearch.stacks.toMutableList()
                if (ingredient.isExpanded) {
                    for (i in 0 until ingredient.items.size) {
                        stacks.removeAt(stacks.indexOf(ingredient) + 1)
                    }
                } else {
                    stacks.addAll(stacks.indexOf(ingredient) + 1, ingredient.items)
                }
                EmiSearch.stacks = stacks
                EmiScreenManager.recalculate()
                ingredient.isExpanded = !ingredient.isExpanded
            }

            else -> {}
        }
    }

}