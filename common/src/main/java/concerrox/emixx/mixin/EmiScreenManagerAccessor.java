package concerrox.emixx.mixin;

import dev.emi.emi.screen.EmiScreenManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = EmiScreenManager.class, remap = false)
public interface EmiScreenManagerAccessor {

    @Accessor("panels")
    static List<EmiScreenManager.SidebarPanel> getPanels() {
        throw new AssertionError();
    }

//    @Accessor("batchers")
//    static StackBatcher.ClaimedCollection getBatchers() {
//        throw new AssertionError();
//    }

}

