package concerrox.emixx.mixin;

import concerrox.emixx.content.StackManager;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.config.SidebarType;
import dev.emi.emi.registry.EmiStackList;
import dev.emi.emi.runtime.EmiSidebars;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = EmiSidebars.class, remap = false)
public class EmiSidebarsMixin {

    @Inject(at = @At("RETURN"), method = "getStacks", cancellable = true)
    private static void getStacks(SidebarType type, CallbackInfoReturnable<List<? extends EmiIngredient>> cir) {
        if (cir.getReturnValue() == EmiStackList.filteredStacks)
            cir.setReturnValue(StackManager.INSTANCE.getSourceStacks());
    }

}
