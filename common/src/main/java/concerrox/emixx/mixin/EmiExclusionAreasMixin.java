package concerrox.emixx.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import concerrox.emixx.EmiPlusPlusScreenManager;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.registry.EmiExclusionAreas;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;

@Mixin(value = EmiExclusionAreas.class, remap = false)
public class EmiExclusionAreasMixin {

    @ModifyExpressionValue(
        method = "getExclusion",
        at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Lists;newArrayList()Ljava/util/ArrayList;")
    )
    private static ArrayList<Bounds> getExclusion(ArrayList<Bounds> original) {
        return EmiPlusPlusScreenManager.INSTANCE.getExclusionAreas(original);
    }

}
