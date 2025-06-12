package concerrox.emixx

import concerrox.emixx.data.ItemGroupDataManager
import concerrox.emixx.data.ItemStackHolder
import concerrox.emixx.stack.ItemGroupEmiStack
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.ItemEmiStack
import dev.emi.emi.registry.EmiStackList
import dev.emi.emi.screen.EmiScreenManager
import dev.emi.emi.search.EmiSearch

object EmiPlusPlusStackManager {

    var sourceStacks: MutableList<EmiIngredient> = EmiStackList.filteredStacks.toMutableList()


    fun updateStacks(stacks: List<EmiIngredient>) {
        sourceStacks = stacks.toMutableList()
        if (EmiScreenManager.search.message.toString().isNotEmpty()) EmiSearch.update()
        EmiSearch.stacks = buildDisplayedStacks()
        EmiScreenManager.recalculate()
    }

    fun buildDisplayedStacks(): List<EmiIngredient> {
        return collectItemGroups(sourceStacks.toMutableList())
    }

    @Suppress("UnstableApiUsage")
    fun collectItemGroups(stacks: MutableList<EmiIngredient>): List<EmiIngredient> {
        // TODO: Use a map to avoid too many loops
        val newStacks = mutableListOf<EmiIngredient>()
        val gps = mutableMapOf<String, ItemGroupEmiStack>()
        stacks.forEach { emiStack ->
            if (emiStack is ItemEmiStack) {
                var doAdd = true
                ItemGroupDataManager.itemGroups.forEach { group ->
                    group.items.forEach { item ->
                        if (item is ItemStackHolder && emiStack.id.toString() == item.id) {
                            val stack = gps.getOrPut(group.id!!) {
                                ItemGroupEmiStack().also {
                                    newStacks += it
                                }
                            }
                            stack.items += emiStack
                            doAdd = false
                        }
                    }
                }
                if (doAdd) newStacks += emiStack
            }
        }
        return newStacks
    }

//    fun collectStacks() : List<EmiIngredient> {
//        return sourceStacks.toMutableList().apply { addAll(displayedStacks) }
//    }
//
//    fun addSpaces() : List<EmiIngredient> {
//        return sourceStacks.toMutableList().apply { addAll(displayedStacks) }
//    }

}