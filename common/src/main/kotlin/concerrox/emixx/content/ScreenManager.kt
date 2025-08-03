package concerrox.emixx.content

import concerrox.emixx.config.EmiPlusPlusConfig
import concerrox.emixx.content.creativemodetab.CreativeModeTabManager
import concerrox.emixx.content.creativemodetab.gui.CreativeModeTabGui
import dev.emi.emi.screen.EmiScreenManager
import net.minecraft.client.gui.screens.Screen

object ScreenManager {

    const val ENTRY_SIZE = 18

    lateinit var screen: Screen
    lateinit var indexScreenSpace: EmiScreenManager.ScreenSpace
    internal val isSearching
        get() = indexScreenSpace.search

    private val isCreativeModeTabEnabled
        get() = EmiPlusPlusConfig.enableCreativeModeTabs.get()

    fun onScreenInitialized(screen: Screen) {
        this.screen = screen
    }

    fun onIndexScreenSpaceCreated(indexScreenSpace: EmiScreenManager.ScreenSpace) {
        this.indexScreenSpace = indexScreenSpace

        if (isCreativeModeTabEnabled) {
            CreativeModeTabGui.initialize(screen)
            CreativeModeTabManager.initialize()
        }
    }

    fun onMouseScrolled(mouseX: Double, mouseY: Double, amount: Double): Boolean {
        return if (isCreativeModeTabEnabled && CreativeModeTabGui.contains(mouseX, mouseY)) {
            CreativeModeTabGui.onMouseScrolled(mouseX, mouseY, amount)
        } else false
    }

}