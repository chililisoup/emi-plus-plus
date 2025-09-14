package concerrox.emixx.config

import com.electronwill.nightconfig.core.AbstractConfig
import concerrox.emixx.EmiPlusPlus
import concerrox.emixx.Minecraft
import concerrox.emixx.content.creativemodetab.gui.CreativeModeTabConfigScreen
import concerrox.emixx.content.stackgroup.gui.StackGroupConfigScreen
import concerrox.emixx.text
import dev.emi.emi.EmiPort
import dev.emi.emi.api.render.EmiTooltipComponents
import dev.emi.emi.config.EmiConfig
import dev.emi.emi.screen.ConfigScreen
import dev.emi.emi.screen.ConfigScreen.Mutator
import dev.emi.emi.screen.widget.config.*
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.contents.TranslatableContents
import net.neoforged.neoforge.common.ModConfigSpec
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue
import java.util.function.Supplier


object ConfigScreenManager {

    private val unsavedChanges = mutableMapOf<ConfigValue<*>, Any>()
    private lateinit var configScreen: ConfigScreen

    fun injectConfigScreen(
        configScreen: ConfigScreen, list: ListWidget, search: ConfigSearch
    ) {
        this.configScreen = configScreen
        val rootName = EmiPlusPlus.MOD_ID
        val rootNameWidget = GroupNameWidget(rootName, text("configuration.title"))
        list.addEntry(rootNameWidget)

        val searcher = { search.search }
        val configSpec = EmiPlusPlusConfig.CONFIG_SPEC
        configSpec.spec.entrySet().forEach { group ->
            val groupKey = group.key
            val groupValue = configSpec.values.get<AbstractConfig>(groupKey)
            val groupNameWidget = SubGroupNameWidget("$rootName.groupName", text("configuration.$groupKey")).apply {
                parent = rootNameWidget
            }
            list.addEntry(groupNameWidget)

            val items = group.getValue<AbstractConfig>().entrySet()
            for (item in items) {
                val itemKey = item.key
                val itemValue = groupValue.get<ConfigValue<Any>>(itemKey)
                val itemTitle = text("configuration.$itemKey")
                val itemTooltip = listOf(EmiTooltipComponents.of(text("configuration.$itemKey.tooltip")))
                val itemWidget = when (itemValue) {
                    is ModConfigSpec.BooleanValue -> BooleanWidget(
                        itemTitle,
                        itemTooltip,
                        searcher,
                        createBM(configScreen, itemValue, EmiPlusPlusConfig.CONFIG_SPEC)
                    )

                    else -> ActionWidget(itemTitle, itemTooltip, searcher) {
                        Minecraft.setScreen(when (itemValue) {
                            EmiPlusPlusConfig.disabledCreativeModeTabs -> CreativeModeTabConfigScreen()
                            EmiPlusPlusConfig.disabledStackGroups -> StackGroupConfigScreen()
                            else -> error("[EMI++] Undefined config screen for $itemKey!")
                        })
                    }
                }?.apply {
                    this.group = EmiConfig.ConfigGroup(groupKey)
                    parentGroups.add(rootNameWidget)
                    rootNameWidget.children.add(this)
                    parentGroups.add(groupNameWidget)
                    groupNameWidget.children.add(this)
                }
                if (item == items.last()) {
                    itemWidget?.endGroup = true
                }
                itemWidget?.apply {
                    list.addEntry(itemWidget)
                }
            }
        }

        unsavedChanges.forEach {

                (key, value) ->
            @Suppress("UNCHECKED_CAST") (key as ConfigValue<Any>).set(value)
        }
    }

    private fun createBM(
        configScreen: ConfigScreen, bv: ModConfigSpec.BooleanValue, configSpec: ModConfigSpec
    ): Mutator<Boolean> {
        return this.configScreen.run {
            object : Mutator<Boolean>() {
                override fun getValue() = bv.get()

                override fun setValue(p0: Boolean) {
                    bv.set(p0)
                    configSpec.save()
                    unsavedChanges[bv] = p0
                }
            }
        }
    }

    class ActionWidget(
        name: Component,
        tooltip: List<ClientTooltipComponent>,
        search: Supplier<String>,
        onClickedListener: Button.OnPress
    ) : ConfigEntryWidget(name, tooltip, search, 20) {

        private val button = EmiPort.newButton(
            0, 0, 150, 20, text("${(name.contents as TranslatableContents).key}.manage"), onClickedListener
        )

        init {
            setChildren(listOf(button))
        }

        override fun update(y: Int, x: Int, width: Int, height: Int) {
            button.x = x + width - button.width
            button.y = y
        }

    }

}