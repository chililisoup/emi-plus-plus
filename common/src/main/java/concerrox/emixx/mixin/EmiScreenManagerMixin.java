package concerrox.emixx.mixin;

import concerrox.emixx.EmiPlusPlusScreenManager;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.config.SidebarSettings;
import dev.emi.emi.runtime.EmiDrawContext;
import dev.emi.emi.screen.EmiScreenManager;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = EmiScreenManager.class, remap = false)
public class EmiScreenManagerMixin {

    @Inject(at = @At("RETURN"), method = "render")
    private static void render(EmiDrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
//        EmiScreenManagerEventHandler.INSTANCE.afterRender(context, mouseX, mouseY, delta);
    }

    @Inject(at = @At("RETURN"), method = "addWidgets")
    private static void addWidgets(Screen screen, CallbackInfo ci) {
        EmiPlusPlusScreenManager.INSTANCE.onEmiPanelsCreated();
    }

    @Inject(at = @At("RETURN"), method = "createScreenSpace")
    private static void recreateScreenSpace(
        EmiScreenManager.SidebarPanel panel, Screen screen, List<Bounds> exclusion, boolean rtl, Bounds bounds,
        SidebarSettings settings, CallbackInfo ci
    ) {
//        if (panel.getType() == SidebarType.INDEX) {
//            panel.space = new EmiScreenManager.ScreenSpace(
//                panel.space.tx,
//                panel.space.ty + 16,
//                panel.space.tw,
//                panel.space.th,
//                rtl,
//                exclusion,
//                panel::getType,
//                panel.isSearch()
//            );
//            panel.spaces = panel.spaces.stream().map(screenSpace -> new EmiScreenManager.ScreenSpace(
//                screenSpace.tx,
//                screenSpace.ty + 16,
//                screenSpace.tw,
//                screenSpace.th,
//                rtl,
//                exclusion,
//                ((EmiScreenManager$ScreenSpaceAccessor) screenSpace).getTypeSupplier(),
//                false
//            )).toList();
//        }
    }

}
