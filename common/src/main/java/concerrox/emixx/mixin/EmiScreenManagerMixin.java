package concerrox.emixx.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import concerrox.emixx.gui.EmiPlusPlusScreenManager;
import concerrox.emixx.stack.ItemGroupEmiStack;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.config.SidebarType;
import dev.emi.emi.runtime.EmiDrawContext;
import dev.emi.emi.screen.EmiScreenBase;
import dev.emi.emi.screen.EmiScreenManager;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(value = EmiScreenManager.class, remap = false)
public class EmiScreenManagerMixin {

    /**
     * Add extra space for the EMI++ header
     */
    @ModifyVariable(at = @At(value = "STORE", ordinal = 0), method = "createScreenSpace", name = "headerOffset")
    private static int modifyHeaderOffset(
        int original, EmiScreenManager.SidebarPanel panel, Screen screen,
        List<Bounds> exclusion
    ) {
        if (panel.getType() == SidebarType.INDEX) {
            return original + EmiPlusPlusScreenManager.EMI_PLUS_PLUS_HEADER_HEIGHT;
        } else {
            return original;
        }
    }

    @Inject(at = @At("HEAD"), method = "addWidgets")
    private static void addEmiPlusPlusWidgets(Screen screen, CallbackInfo ci) {
        EmiPlusPlusScreenManager.INSTANCE.addEmiPlusPlusWidgets(screen);
    }

    @Inject(at = @At("RETURN"), method = "mouseScrolled", cancellable = true)
    private static void mouseScrolled(
        double mouseX, double mouseY, double amount,
        CallbackInfoReturnable<Boolean> cir
    ) {
        cir.setReturnValue(
            cir.getReturnValueZ() || EmiPlusPlusScreenManager.INSTANCE.onMouseScrolled(mouseX, mouseY, amount));
    }

    @WrapOperation(
        at = @At(value = "INVOKE", target = "Ldev/emi/emi/api/stack/EmiIngredient;isEmpty()Z", ordinal = 0),
        method = "mouseReleased"
    )
    private static boolean modifyMouseReleased(EmiIngredient instance, Operation<Boolean> original) {
        if (instance instanceof ItemGroupEmiStack)
            EmiPlusPlusScreenManager.INSTANCE.onStackInteraction(instance);
        return original.call(instance);
    }

}
