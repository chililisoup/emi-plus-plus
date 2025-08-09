package concerrox.emixx.content.stackgroup.data

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.crafting.Ingredient

class SimpleItemGroup(
    id: ResourceLocation, type: Type,
    val targets: List<Ingredient>,
) : StackGroup(id, type) {

    @Deprecated("Use constructor that takes a ResourceLocation")
    constructor(id: String, type: Type, targets: List<Ingredient>) : this(
        ResourceLocation.withDefaultNamespace(id),
        type,
        targets
    )

    companion object {
        val CODEC: Codec<SimpleItemGroup> = RecordCodecBuilder.create {
            it.group(
                ResourceLocation.CODEC.fieldOf("id").forGetter(SimpleItemGroup::id),
                Type.CODEC.fieldOf("type").forGetter(SimpleItemGroup::type),
                Ingredient.CODEC_NONEMPTY.listOf().fieldOf("contents").forGetter(SimpleItemGroup::targets),
            ).apply(it, ::SimpleItemGroup)
        }
    }

    override fun match(stack: EmiIngredient): Boolean {
        if (stack !is EmiStack) return false
        return targets.any { it.test(stack.itemStack) }
    }

}