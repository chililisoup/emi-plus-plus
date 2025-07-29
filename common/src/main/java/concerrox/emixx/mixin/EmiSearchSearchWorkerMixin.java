package concerrox.emixx.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import concerrox.emixx.StackManager;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.config.SidebarType;
import dev.emi.emi.runtime.EmiSidebars;
import dev.emi.emi.screen.EmiScreenManager;
import dev.emi.emi.search.EmiSearch;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(targets = "dev.emi.emi.search.EmiSearch$SearchWorker", remap = false)
public class EmiSearchSearchWorkerMixin {

    @Shadow
    @Final
    private String query;

    @WrapOperation(method = "run", at = @At(value = "INVOKE", target = "Ldev/emi/emi/search/EmiSearch;apply(Ldev/emi/emi/search/EmiSearch$SearchWorker;Ljava/util/List;)V"))
    public void run(EmiSearch.SearchWorker worker, List<? extends EmiIngredient> stacks, Operation<Void> original) {
        synchronized (EmiSearch.class) {
            if (EmiScreenManager.getSearchSource() == EmiSidebars.getStacks(SidebarType.INDEX))
                original.call(worker, StackManager.INSTANCE.onEmiIndexSearch(query, stacks));
        }
    }

}
