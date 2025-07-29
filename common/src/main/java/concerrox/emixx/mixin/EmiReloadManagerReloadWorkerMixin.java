package concerrox.emixx.mixin;

import concerrox.emixx.EmiPlusPlus;
import concerrox.emixx.EmiPlusPlusKt;
import concerrox.emixx.content.stackgroup.StackGroupManager;
import dev.emi.emi.runtime.EmiReloadManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "dev.emi.emi.runtime.EmiReloadManager$ReloadWorker", remap = false)
public class EmiReloadManagerReloadWorkerMixin {

    @Inject(method = "run", at = @At(value = "TAIL", target = "Ldev/emi/emi/registry/EmiStackList;bake()V"))
    public void run(CallbackInfo ci) {
        EmiPlusPlus.INSTANCE.getLOGGER$emixx_common().info("Starting EMI++ reloading…");
        EmiReloadManager.step(EmiPlusPlusKt.text("gui", "baking_item_groups"), 10_000);
        StackGroupManager.INSTANCE.reload();
    }

}
