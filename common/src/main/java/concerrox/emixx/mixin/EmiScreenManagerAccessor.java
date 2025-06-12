package concerrox.emixx.mixin;

import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.screen.EmiScreenManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = EmiScreenManager.class, remap = false)
public interface EmiScreenManagerAccessor {

    @Accessor("searchedStacks")
    static void setSearchedStacks(List<? extends EmiIngredient> stacks) {

    }

}
