package concerrox.emixx.mixin;

import concerrox.emixx.content.ScreenManager;
import dev.emi.emi.config.SidebarType;
import dev.emi.emi.screen.EmiScreenManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = EmiScreenManager.SidebarPanel.class, remap = false)
public abstract class SidebarPanelMixin {

    @Shadow
    public abstract SidebarType getType();

    @Shadow
    public EmiScreenManager.ScreenSpace space;

    @Inject(at = @At("TAIL"), method = "setSpaces")
    private void addEmiPlusPlusWidgets(EmiScreenManager.ScreenSpace main, List<EmiScreenManager.ScreenSpace> subpanels, CallbackInfo ci) {
        if (getType() == SidebarType.INDEX) {
            ScreenManager.INSTANCE.onIndexScreenSpaceCreated(space);
        }
    }

}
