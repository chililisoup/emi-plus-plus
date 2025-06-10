package concerrox.emixx.mixin;

import concerrox.emixx.EmiScreenManagerEventHandler;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerScreen.class)
public class AbstractContainerScreenMixin {

    @Inject(at = @At("RETURN"), method = "tick")
    private void init(CallbackInfo ci) {
        EmiScreenManagerEventHandler.INSTANCE.afterScreenTicked();
    }

}