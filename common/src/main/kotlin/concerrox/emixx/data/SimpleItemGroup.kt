package concerrox.emixx.data

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import dev.emi.emi.api.stack.EmiIngredient
import dev.emi.emi.api.stack.EmiStack
import net.minecraft.world.item.crafting.Ingredient

class SimpleItemGroup(
    id: String, type: Type,
    val targets: List<Ingredient>,
) : StackGroup(id, type) {

    companion object {
        val CODEC: Codec<SimpleItemGroup> = RecordCodecBuilder.create {
            it.group(
                Codec.STRING.fieldOf("id").forGetter(SimpleItemGroup::id),
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