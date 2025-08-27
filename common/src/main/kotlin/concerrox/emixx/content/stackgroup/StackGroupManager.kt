package concerrox.emixx.content.stackgroup

import com.google.gson.JsonParser
import com.mojang.serialization.JsonOps
import concerrox.emixx.config.EmiPlusPlusConfig
import concerrox.emixx.content.stackgroup.data.*
import concerrox.emixx.registry.ModTags
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.resources.ResourceLocation
import net.minecraft.tags.ItemTags
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import kotlin.io.path.*

object StackGroupManager {

    private val STACK_GROUP_DIRECTORY_PATH = EmiPlusPlusConfig.CONFIG_DIRECTORY_PATH / "groups"
    private val DEFAULT_STACK_GROUPS = arrayOf(
        SimpleItemGroup("enchanted_books", listOf(Ingredient.of(Items.ENCHANTED_BOOK))),
        SimpleItemGroup(
            "potions", listOf(Ingredient.of(Items.POTION), Ingredient.of(Items.OMINOUS_BOTTLE))
        ),
        SimpleItemGroup("splash_potions", listOf(Ingredient.of(Items.SPLASH_POTION))),
        SimpleItemGroup("lingering_potions", listOf(Ingredient.of(Items.LINGERING_POTION))),
        SimpleItemGroup("tipped_arrows", listOf(Ingredient.of(Items.TIPPED_ARROW))),
        EmiStackGroup.of(ModTags.Item.MUSIC_DISCS),

        EmiStackGroup.of(ItemTags.SHOVELS),
        EmiStackGroup.of(ItemTags.PICKAXES),
        EmiStackGroup.of(ItemTags.AXES),
        EmiStackGroup.of(ItemTags.SWORDS),
        EmiStackGroup.of(ItemTags.HOES),
        EmiStackGroup.of(ItemTags.HEAD_ARMOR),
        EmiStackGroup.of(ItemTags.CHEST_ARMOR),
        EmiStackGroup.of(ItemTags.LEG_ARMOR),
        EmiStackGroup.of(ItemTags.FOOT_ARMOR),
        AnimalArmorItemGroup(),

        InfestedBlockItemGroup(),
        EmiStackGroup.of(ModTags.Item.RAW_MATERIALS),
        EmiStackGroup.of(ModTags.Item.FOODS),
        EmiStackGroup.of(ItemTags.PLANKS),
        EmiStackGroup.of(ItemTags.STAIRS),
        EmiStackGroup.of(ItemTags.SLABS),
        EmiStackGroup.of(ItemTags.FENCES),
        EmiStackGroup.of(ItemTags.FENCE_GATES),
        EmiStackGroup.of(ItemTags.DOORS),
        EmiStackGroup.of(ItemTags.TRAPDOORS),
        PressurePlateItemGroup(),
        MinecartItemGroup(),
        EmiStackGroup.of(ItemTags.SKULLS),
        EmiStackGroup.of(ItemTags.RAILS),
        EmiStackGroup.of(ModTags.Item.DYES),
        EmiStackGroup.of(ItemTags.BUTTONS),
        EmiStackGroup.of(ItemTags.SAPLINGS),
        EmiStackGroup.of(ModTags.Item.ORES),
        EmiStackGroup.of(ModTags.Item.SEEDS),
        EmiStackGroup.of(ItemTags.LOGS),
        EmiStackGroup.of(ItemTags.LEAVES),
        EmiStackGroup.of(ItemTags.SIGNS),
        EmiStackGroup.of(ItemTags.HANGING_SIGNS),
        EmiStackGroup.of(ItemTags.BOATS),
        EmiStackGroup.of(ItemTags.WALLS),
        EmiStackGroup.of(ModTags.Item.GLASS_BLOCKS),
        EmiStackGroup.of(ModTags.Item.GLASS_PANES),
        EmiStackGroup.of(ItemTags.WOOL),
        EmiStackGroup.of(ItemTags.FLOWERS),
        EmiStackGroup.of(ItemTags.TERRACOTTA),
        EmiStackGroup.of(ItemTags.WOOL_CARPETS),
        SimpleItemGroup("goat_horns", listOf(Ingredient.of(Items.GOAT_HORN))),
        SimpleItemGroup("suspicious_stews", listOf(Ingredient.of(Items.SUSPICIOUS_STEW))),
        EmiStackGroup.of(ItemTags.BANNERS),
        EmiStackGroup.of(ModTags.Item.SHULKER_BOXES),
        EmiStackGroup.of(ModTags.Item.CONCRETES),
        EmiStackGroup.of(ModTags.Item.CONCRETE_POWDERS),
        EmiStackGroup.of(ModTags.Item.GLAZED_TERRACOTTAS),
        EmiStackGroup.of(ItemTags.BEDS),
        EmiStackGroup.of(ItemTags.CANDLES),
        SimpleItemGroup("paintings", listOf(Ingredient.of(Items.PAINTING))),
        EmiStackGroup.of(ItemTags.DECORATED_POT_SHERDS),
        EmiStackGroup.of(ItemTags.TRIM_TEMPLATES),
        EmiStackGroup.of(ModTags.Item.BUCKETS),
        EmiStackGroup.of(ModTags.Item.DUSTS),
        EmiStackGroup.of(ModTags.Item.NUGGETS),
        EmiStackGroup.of(ModTags.Item.INGOTS),
        BannerPatternItemGroup(),
        SpawnEggItemGroup(),
        CopperBlockItemGroup(),

        // Mekanism
        EmiStackGroup.of(ModTags.Item.MEKANISM_UNITS),
        EmiStackGroup.of(ModTags.Item.MEKANISM_DIRTY_DUSTS),
        EmiStackGroup.of(ModTags.Item.MEKANISM_CLUMPS),
        EmiStackGroup.of(ModTags.Item.MEKANISM_CRYSTALS),
        EmiStackGroup.of(ModTags.Item.MEKANISM_ENRICHED),
        EmiStackGroup.of(ModTags.Item.MEKANISM_SHARDS),
    )

    private val stackGroups = mutableListOf<StackGroup>()
    internal var groupToGroupStacks = mapOf<StackGroup, EmiGroupStack>()
    private var disabledStackGroups = listOf<ResourceLocation>()

    fun reload() {
        disabledStackGroups = EmiPlusPlusConfig.disabledStackGroups.get().map { ResourceLocation.parse(it) }
        stackGroups.clear()
        stackGroups.addAll(DEFAULT_STACK_GROUPS)
//        stackGroups.addAll(DEFAULT_STACK_GROUPS.filter { disabledStackGroups.contains(it.id) })
        STACK_GROUP_DIRECTORY_PATH.createDirectories().listDirectoryEntries("*.json").forEach {
            val json = JsonParser.parseString(it.readText())
            val result = EmiStackGroup.parse(json, it.fileName)
            if (result != null) stackGroups += result
        }
    }

    internal fun buildGroupedStacks(source: List<EmiStack>): List<EmiStack> {
        val result = mutableListOf<EmiStack>()
        val addedGroupStacks = mutableListOf<EmiGroupStack>()
        groupToGroupStacks = stackGroups.associateWith { group -> EmiGroupStack(group) }
        for (stack in source) {
            var shouldAddStack = true
            for (stackGroup in stackGroups) {
                val groupStack = groupToGroupStacks[stackGroup]!!
                if (stackGroup.match(stack)) {
                    if (!disabledStackGroups.contains(stackGroup.id)) shouldAddStack =
                        false // Once a stack matches a stackGroup, it shouldn't be added to the index page
                    groupStack.items += GroupedEmiStack(stack, stackGroup)
                    if (groupStack !in addedGroupStacks) {
                        addedGroupStacks += groupStack
                        if (!disabledStackGroups.contains(stackGroup.id)) result += groupStack
                    }
                }
            }
            if (shouldAddStack) result += stack
        }
        return result
    }

    @Deprecated("Will be removed after the refactor of stack groups")
    internal fun buildGroupedStacksForConfig(source: List<EmiStack>): Map<StackGroup, EmiGroupStack> {
        val localGroupToGroupStacks = stackGroups.associateWith { group -> EmiGroupStack(group) }
        for (stack in source) {
            for (stackGroup in stackGroups) {
                val groupStack = localGroupToGroupStacks[stackGroup]!!
                if (stackGroup.match(stack)) groupStack.items += GroupedEmiStack(stack, stackGroup)
            }
        }
        return localGroupToGroupStacks
    }

    @Deprecated("Will be removed after the refactor of stack groups")
    internal fun create(tag: TagKey<Item>) {
        SimpleItemGroup.CODEC.encodeStart(
            JsonOps.INSTANCE, SimpleItemGroup(tag.location, listOf(Ingredient.of(tag)))
        )
            .ifSuccess { ret ->
                (STACK_GROUP_DIRECTORY_PATH / (tag.location.path.replace("/", "__") + ".json")).writeText(ret.toString())
            }
    }

}