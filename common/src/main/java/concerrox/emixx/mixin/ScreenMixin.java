package concerrox.emixx.mixin;

import concerrox.emixx.EmiPlusPlusScreenManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {

    @Inject(at = @At("RETURN"), method = "init(Lnet/minecraft/client/Minecraft;II)V")
    private void init(Minecraft minecraft, int width, int height, CallbackInfo ci) {
        if ((Object) this instanceof AbstractContainerScreen<?> hs) {
            EmiPlusPlusScreenManager.INSTANCE.onScreenInitialized(hs);
        }
    }




//
//    @Inject(at = @At("RETURN"), method = "resize(Lnet/minecraft/client/MinecraftClient;II)V")
//    private void resize(MinecraftClient client, int width, int height, CallbackInfo info) {
//        if ((Object) this instanceof HandledScreen hs && client.currentScreen == hs) {
//            EmiScreenManager.addWidgets(hs);
//        }
//    }
}