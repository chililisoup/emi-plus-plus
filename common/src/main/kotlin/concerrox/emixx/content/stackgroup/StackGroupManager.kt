package concerrox.emixx.content.stackgroup

import com.google.gson.JsonParser
import com.mojang.serialization.JsonOps
import concerrox.emixx.config.EmiPlusPlusConfig
import concerrox.emixx.data.*
import concerrox.emixx.registry.ModTags
import concerrox.emixx.stack.ItemGroupEmiStack
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import dev.emi.emi.registry.EmiStackList
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import kotlin.io.path.div
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.readText

object StackGroupManager {

    private val STACK_GROUP_DIRECTORY_PATH = EmiPlusPlusConfig.CONFIG_DIRECTORY_PATH / "groups"
    private val DEFAULT_STACK_GROUPS = arrayOf(
        SimpleItemGroup("enchanted_books", StackGroup.Type.ITEM, listOf(Ingredient.of(Items.ENCHANTED_BOOK))),
        SimpleItemGroup(
            "potions", StackGroup.Type.ITEM, listOf(Ingredient.of(Items.POTION), Ingredient.of(Items.OMINOUS_BOTTLE))
        ),
        SimpleItemGroup("splash_potions", StackGroup.Type.ITEM, listOf(Ingredient.of(Items.SPLASH_POTION))),
        SimpleItemGroup("lingering_potions", StackGroup.Type.ITEM, listOf(Ingredient.of(Items.LINGERING_POTION))),
        SimpleItemGroup("tipped_arrows", StackGroup.Type.ITEM, listOf(Ingredient.of(Items.TIPPED_ARROW))),
        SimpleItemGroup("music_discs", StackGroup.Type.ITEM, listOf(Ingredient.of(ModTags.Item.MUSIC_DISCS))),

        SimpleItemGroup("shovels", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.SHOVELS))),
        SimpleItemGroup("pickaxes", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.PICKAXES))),
        SimpleItemGroup("axes", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.AXES))),
        SimpleItemGroup("swords", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.SWORDS))),
        SimpleItemGroup("hoes", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.HOES))),
        SimpleItemGroup("head_armors", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.HEAD_ARMOR))),
        SimpleItemGroup("chest_armors", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.CHEST_ARMOR))),
        SimpleItemGroup("leg_armors", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.LEG_ARMOR))),
        SimpleItemGroup("foot_armors", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.FOOT_ARMOR))),
        AnimalArmorItemGroup(),

        InfestedBlockItemGroup(),
        SimpleItemGroup("raw_materials", StackGroup.Type.ITEM, listOf(Ingredient.of(ModTags.Item.RAW_MATERIALS))),
        SimpleItemGroup("foods", StackGroup.Type.ITEM, listOf(Ingredient.of(ModTags.Item.FOODS))),
        SimpleItemGroup("planks", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.PLANKS))),
        SimpleItemGroup("stairs", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.STAIRS))),
        SimpleItemGroup("slabs", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.SLABS))),
        SimpleItemGroup("fences", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.FENCES))),
        SimpleItemGroup("fence_gates", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.FENCE_GATES))),
        SimpleItemGroup("doors", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.DOORS))),
        SimpleItemGroup("trapdoors", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.TRAPDOORS))),
        PressurePlateItemGroup(),
        MinecartItemGroup(),
        SimpleItemGroup("skulls", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.SKULLS))),
        SimpleItemGroup("rails", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.RAILS))),
        SimpleItemGroup("dyes", StackGroup.Type.ITEM, listOf(Ingredient.of(ModTags.Item.DYES))),
        SimpleItemGroup("buttons", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.BUTTONS))),
        SimpleItemGroup("saplings", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.SAPLINGS))),
        SimpleItemGroup("ores", StackGroup.Type.ITEM, listOf(Ingredient.of(ModTags.Item.ORES))),
        SimpleItemGroup("seeds", StackGroup.Type.ITEM, listOf(Ingredient.of(ModTags.Item.SEEDS))),
        SimpleItemGroup("logs", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.LOGS))),
        SimpleItemGroup("leaves", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.LEAVES))),
        SimpleItemGroup("signs", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.SIGNS))),
        SimpleItemGroup("hanging_signs", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.HANGING_SIGNS))),
        SimpleItemGroup("boats", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.BOATS))),
        SimpleItemGroup("walls", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.WALLS))),
        SimpleItemGroup("glass_blocks", StackGroup.Type.ITEM, listOf(Ingredient.of(ModTags.Item.GLASS_BLOCKS))),
        SimpleItemGroup("glass_panes", StackGroup.Type.ITEM, listOf(Ingredient.of(ModTags.Item.GLASS_PANES))),
        SimpleItemGroup("wools", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.WOOL))),
        SimpleItemGroup("flowers", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.FLOWERS))),
        SimpleItemGroup("terracottas", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.TERRACOTTA))),
        SimpleItemGroup("wool_carpets", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.WOOL_CARPETS))),
        SimpleItemGroup("goat_horns", StackGroup.Type.ITEM, listOf(Ingredient.of(Items.GOAT_HORN))),
        SimpleItemGroup("suspicious_stews", StackGroup.Type.ITEM, listOf(Ingredient.of(Items.SUSPICIOUS_STEW))),
        SimpleItemGroup("banners", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.BANNERS))),
        SimpleItemGroup("shulker_boxes", StackGroup.Type.ITEM, listOf(Ingredient.of(ModTags.Item.SHULKER_BOXES))),
        SimpleItemGroup("concretes", StackGroup.Type.ITEM, listOf(Ingredient.of(ModTags.Item.CONCRETES))),
        SimpleItemGroup("concrete_powders", StackGroup.Type.ITEM, listOf(Ingredient.of(ModTags.Item.CONCRETE_POWDERS))),
        SimpleItemGroup(
            "glazed_terracottas", StackGroup.Type.ITEM, listOf(Ingredient.of(ModTags.Item.GLAZED_TERRACOTTAS))
        ),
        SimpleItemGroup("beds", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.BEDS))),
        SimpleItemGroup("candles", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.CANDLES))),
        SimpleItemGroup("paintings", StackGroup.Type.ITEM, listOf(Ingredient.of(Items.PAINTING))),
        SimpleItemGroup(
            "decorated_pot_sherds", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.DECORATED_POT_SHERDS))
        ),
        SimpleItemGroup("trim_templates", StackGroup.Type.ITEM, listOf(Ingredient.of(ItemTags.TRIM_TEMPLATES))),
        SimpleItemGroup("buckets", StackGroup.Type.ITEM, listOf(Ingredient.of(ModTags.Item.BUCKETS))),
        BannerPatternItemGroup(),
        SpawnEggItemGroup(),
        CopperBlockItemGroup(),
    )

    private val stackGroups = mutableListOf<StackGroup>()
    internal var groupToGroupStacks = mapOf<StackGroup, ItemGroupEmiStack>()
    internal var groupedStacks = listOf<EmiIngredient>()
    internal var disabledStackGroups = listOf<String>()

    fun reload() {
        disabledStackGroups = EmiPlusPlusConfig.disabledStackGroups.get()
        stackGroups.clear()
        stackGroups.addAll(DEFAULT_STACK_GROUPS)
        groupToGroupStacks = stackGroups.associateWith { group -> ItemGroupEmiStack(group) }
//        stackGroups.addAll(DEFAULT_STACK_GROUPS.filter { disabledStackGroups.contains(it.id) })
        STACK_GROUP_DIRECTORY_PATH.listDirectoryEntries("*.json").forEach {
            val json = JsonParser.parseString(it.readText())
            SimpleItemGroup.CODEC.parse(JsonOps.INSTANCE, json).ifSuccess { group -> stackGroups += group }
            groupedStacks = buildGroupedStacks(EmiStackList.filteredStacks)
        }
    }

    internal fun buildGroupedStacks(source: List<EmiIngredient>): List<EmiIngredient> {
        val result = mutableListOf<EmiIngredient>()
        val addedGroupStacks = mutableListOf<ItemGroupEmiStack>()
        groupToGroupStacks = stackGroups.associateWith { group -> ItemGroupEmiStack(group) }
        for (stack in source) {
            var shouldAddStack = true
            for (stackGroup in stackGroups) {
                val groupStack = groupToGroupStacks[stackGroup]!!
                if (stack is EmiStack && stackGroup.match(stack)) {
                    if (!disabledStackGroups.contains(stackGroup.id)) shouldAddStack =
                        false // Once a stack matches a stackGroup, it shouldn't be added to the index page
                    groupStack.items += stack
                    if (groupStack !in addedGroupStacks) {
                        addedGroupStacks += groupStack
                        if (!disabledStackGroups.contains(stackGroup.id))
                            result += groupStack
                    }
                }
            }
            if (shouldAddStack) result += stack
        }
        return result
    }

}