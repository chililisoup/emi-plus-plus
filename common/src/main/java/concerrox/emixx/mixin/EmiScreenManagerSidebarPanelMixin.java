package concerrox.emixx.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import concerrox.emixx.content.ScreenManager;
import dev.emi.emi.config.SidebarType;
import dev.emi.emi.screen.EmiScreenManager;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EmiScreenManager.SidebarPanel.class)
public abstract class EmiScreenManagerSidebarPanelMixin {

    @Shadow
    public abstract SidebarType getType();

    @WrapOperation(method = "drawHeader", at = @At(value = "INVOKE",
            target = "Ldev/emi/emi/EmiRenderHelper;getPageText(III)Lnet/minecraft/network/chat/Component;"))
    private Component replaceIndexHeader(int page, int total, int maxWidth, Operation<Component> original) {
        if (getType() == SidebarType.INDEX && ScreenManager.INSTANCE.getCustomIndexTitle$emixx_common() != null)
            return ScreenManager.INSTANCE.getCustomIndexTitle$emixx_common();
        return original.call(page, total, maxWidth);
    }

}
