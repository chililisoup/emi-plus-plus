package concerrox.emixx.mixin;

import dev.emi.emi.screen.EmiScreenManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EmiScreenManager.ScreenSpace.class, remap = false)
public interface EmiScreenManager$ScreenSpaceAccessor {

//    @Accessor("typeSupplier")
//    Supplier<SidebarType> getTypeSupplier();

}
