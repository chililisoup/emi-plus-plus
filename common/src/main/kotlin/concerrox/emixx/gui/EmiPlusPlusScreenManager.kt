package concerrox.emixx.gui

import concerrox.emixx.EmiPlusPlus
import concerrox.emixx.EmiPlusPlusStackManager
import concerrox.emixx.gui.components.ImageButtonWidget
import concerrox.emixx.gui.components.tabs.ItemTab
import concerrox.emixx.gui.components.tabs.ItemTabManager
import concerrox.emixx.gui.components.tabs.ItemTabNavigationBar
import concerrox.emixx.stack.ItemGroupEmiStack
import concerrox.emixx.stack.TextStack
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.registry.EmiStackList
import dev.emi.emi.runtime.EmiDrawContext
import dev.emi.emi.screen.EmiScreenBase
import dev.emi.emi.screen.EmiScreenManager
import dev.emi.emi.search.EmiSearch
import net.minecraft.client.gui.screens.Screen
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.CreativeModeTabs


object EmiPlusPlusScreenManager {

    const val EMI_PLUS_PLUS_HEADER_HEIGHT = 18
    private const val EMI_HEADER_HEIGHT = 18
    const val TILE_SIZE = 18
    private val FILTERED_CREATIVE_MODE_TABS = setOf(CreativeModeTabs.INVENTORY, CreativeModeTabs.HOTBAR,
        CreativeModeTabs.OP_BLOCKS, CreativeModeTabs.SEARCH).map(BuiltInRegistries.CREATIVE_MODE_TAB::get)

    private var emiScreen: Screen? = null
    private var indexSpace: EmiScreenManager.ScreenSpace? = null

    private val tabManager = ItemTabManager({ emiScreen?.addRenderableWidget(it) },
        { emiScreen?.removeWidget(it) }).apply {
        onTabSelectedListener = this@EmiPlusPlusScreenManager::onTabSelected
    }
    private val tabNavigationBar = ItemTabNavigationBar(tabManager)
    private var currentTabPage = 0
    private var lastTabPage = 0
    private var tabCount = 0
    private val indexCreativeModeTab = BuiltInRegistries.CREATIVE_MODE_TAB.get(CreativeModeTabs.SEARCH)
    private val vanillaCreativeModeTabs = BuiltInRegistries.CREATIVE_MODE_TAB.toMutableList().apply {
        removeIf { it in FILTERED_CREATIVE_MODE_TABS }
        addFirst(indexCreativeModeTab)
    }

    private var scrollAccumulator = 0.0

    private val buttonPrevious = ImageButtonWidget(16, 16, 0, 0, { true }, { previousPage() })
    private val buttonNext = ImageButtonWidget(16, 16, 16, 0, { true }, { nextPage() })

//    private val btn = SizedButtonWidget(0, 0, 20, 20, 184, 0, { true }, { _ ->
//        val space = EmiScreenManager.getSearchPanel().getSpaces()[0]
//        val stacks = mutableListOf<EmiIngredient>()
////        ContentData.contents[ContentData.tabs["create"]]!!.forEach { type ->
////            stacks.add(TextStack(Component.translatable(type.key)))
////            for (i in 1 until space.tw) stacks.add(BlankStack())
////            val items = type.value.map {
////                val id = (it as ItemStackHolder).id
////                EmiStack.of(BuiltInRegistries.ITEM.get(ResourceLocation.parse(id)))
////            }
////            stacks.addAll(items)
////            val endSpaces = space.tw - items.size % space.tw
////            for (i in 1..endSpaces) stacks.add(BlankStack())
////        }
//        stacks.add(ItemCollectionStack())
//        updateStacks(stacks)
//    }, listOf(EmiPort.translatable("tooltip.emi.recipe_tree")))

    fun add2(sidebarPanel: EmiScreenManager.SidebarPanel) {
        val emiScreen = emiScreen
        val indexSpace = sidebarPanel.space?.also { indexSpace = it }
        if (emiScreen != null && indexSpace != null) {
            val tx = indexSpace.tx
            val ty = indexSpace.ty - EMI_HEADER_HEIGHT - EMI_PLUS_PLUS_HEADER_HEIGHT
            val tw = indexSpace.tw
            tabCount = tw - 2
            emiScreen.addRenderableWidget(buttonPrevious.apply {
                x = tx
                y = ty + 2
            })
            emiScreen.addRenderableWidget(tabNavigationBar.apply {
                width = tabCount * TILE_SIZE + 4
                x = tx + buttonPrevious.width
                y = ty
            })
            emiScreen.addRenderableWidget(buttonNext.apply {
                x = tabNavigationBar.x + tabNavigationBar.width
                y = ty + 2
            })

            lastTabPage = vanillaCreativeModeTabs.size / tabCount
            currentTabPage = 0
            updateTabs()
            tabNavigationBar.selectTab(0, false)
            tabNavigationBar.tabButtons[0].isFocused = true
            onTabSelected(tabNavigationBar.tabs[0] as ItemTab)
        }
    }

    private fun onTabSelected(tab: ItemTab) {
        if (tab.creativeModeTab == indexCreativeModeTab) {
            EmiPlusPlusStackManager.updateStacks(EmiStackList.filteredStacks) // Use EMI's default index stacks
        } else {
            tab.creativeModeTab?.displayItems?.map(EmiStack::of)?.let(EmiPlusPlusStackManager::updateStacks)
        }
    }

    private fun updateTabs() {
        val tabs = mutableListOf<ItemTab>()
        for (i in 0 until tabCount) tabs.add(ItemTab(vanillaCreativeModeTabs.getOrNull(currentTabPage * tabCount + i)))
        tabNavigationBar.setTabs(tabs)
        tabNavigationBar.arrangeElements()
    }

    fun addEmiPlusPlusWidgets(screen: Screen) {
        emiScreen = screen
//        screen.addWidget(btn)
//        btn.x = 50
//        btn.y = 50
    }

    fun renderEmiPlusPlusWidgets(context: EmiDrawContext, mouseX: Int, mouseY: Int, delta: Float, base: EmiScreenBase) {
//        context.push()
//        context.matrices().translate(0.0, 0.0, 100.0)
//        btn.render(context.raw(), mouseX, mouseY, delta)
//        context.pop()
    }

    fun onMouseScrolled(mouseX: Double, mouseY: Double, amount: Double): Boolean {
        val indexSpace = indexSpace ?: return false
        scrollAccumulator += amount
        val sa = scrollAccumulator.toInt() // Decelerate the scroll so that it doesn't get too fast
        scrollAccumulator %= 1
        val xRange = indexSpace.tx.toDouble()..(indexSpace.tx + indexSpace.tw * TILE_SIZE).toDouble()
        val yRange = (indexSpace.ty - EMI_HEADER_HEIGHT - EMI_PLUS_PLUS_HEADER_HEIGHT).toDouble()..(indexSpace.ty - EMI_HEADER_HEIGHT).toDouble()
        if (xRange.contains(mouseX) && yRange.contains(mouseY)) {
            if (sa > 0) previousPage() else if (sa < 0) nextPage()
            return true
        }
        return false
    }

    private fun nextPage() {
        if (currentTabPage < lastTabPage) currentTabPage++ else currentTabPage = 0
        updateTabs()
    }

    private fun previousPage() {
        if (currentTabPage > 0) currentTabPage-- else currentTabPage = lastTabPage
        updateTabs()
    }

    fun onStackInteraction(ingredient: EmiIngredient) {
        EmiPlusPlus.LOGGER.info("onStackInteraction: $ingredient")
        when (ingredient) {
            is TextStack -> {}
            is ItemGroupEmiStack -> {
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