package concerrox.emixx.mixin;

import dev.emi.emi.api.EmiApi;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.bom.BoM;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(value = EmiApi.class, remap = false)
public abstract class EmiApiMixin {

    @Shadow
    private static void setPages(Map<EmiRecipeCategory, List<EmiRecipe>> recipes, EmiIngredient stack) {
    }

    @Shadow
    private static Map<EmiRecipeCategory, List<EmiRecipe>> mapRecipes(List<EmiRecipe> list) {
        return null;
    }

    @Shadow
    private static List<EmiRecipe> pruneSources(List<EmiRecipe> list, EmiStack context) {
        return null;
    }

    @Shadow
    public static void focusRecipe(EmiRecipe recipe) {
    }

    @Inject(method = "displayRecipes", at = @At("TAIL"))
    private static void displayMoreThanOneEmiStacks(EmiIngredient stack, CallbackInfo ci) {
        if (stack.getEmiStacks().size() > 1) {
            EmiStack es = stack.getEmiStacks().get(1);
            setPages(mapRecipes(pruneSources(EmiApi.getRecipeManager().getRecipesByOutput(es), es)), stack);
            focusRecipe(BoM.getRecipe(es));
        }
    }

}



