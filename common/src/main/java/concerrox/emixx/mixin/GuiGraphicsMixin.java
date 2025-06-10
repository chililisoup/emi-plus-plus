package concerrox.emixx.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import concerrox.emixx.EmiPlusPlus;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public class GuiGraphicsMixin {

    // Disable!!!!!!!!!
    @Inject(method = "innerBlit(Lnet/minecraft/resources/ResourceLocation;IIIIIFFFF)V", at = @At("HEAD"))
    void drawTexturedQuad(
        ResourceLocation atlasLocation, int x1, int x2, int y1, int y2, int blitOffset, float minU, float maxU,
        float minV, float maxV, CallbackInfo ci
    ) {
        if (atlasLocation.getNamespace().equals(EmiPlusPlus.MOD_ID)) {
            RenderSystem.enableBlend();
        }
    }

    @Inject(method = "innerBlit(Lnet/minecraft/resources/ResourceLocation;IIIIIFFFFFFFF)V", at = @At("HEAD"))
    void drawTexturedQuad(
        ResourceLocation atlasLocation, int x1, int x2, int y1, int y2, int blitOffset, float minU, float maxU,
        float minV, float maxV, float red, float green, float blue, float alpha, CallbackInfo ci
    ) {
        if (atlasLocation.getNamespace().equals(EmiPlusPlus.MOD_ID)) {
            RenderSystem.enableBlend();
        }
    }

}