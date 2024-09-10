package mods.thecomputerizer.theimpossiblelibrary.fabric.core.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CustomFabricEvents.RENDER_DEBUG_INFO;

@Mixin(DebugScreenOverlay.class)
public abstract class MixinDebugScreenOverlay {
    
    @Shadow protected abstract List<String> getGameInformation();
    @Shadow protected abstract List<String> getSystemInformation();
    
    @Unique private final List<String> theimpossiblelibrary$left = new ArrayList<>();
    @Unique private final List<String> theimpossiblelibrary$right = new ArrayList<>();
    
    @Redirect(at =@At(value="INVOKE", target="Lnet/minecraft/client/gui/components/DebugScreenOverlay;"+
                                   "getGameInformation()Ljava/util/List;"), method = "drawGameInformation")
    private List<String> theimpossiblelibrary$getGameInformation(DebugScreenOverlay overlay) {
        return this.theimpossiblelibrary$left;
    }
    
    @Redirect(at =@At(value="INVOKE", target="Lnet/minecraft/client/gui/components/DebugScreenOverlay;"+
                                             "getSystemInformation()Ljava/util/List;"), method = "drawSystemInformation")
    private List<String> theimpossiblelibrary$getSystemInformation(DebugScreenOverlay overlay) {
        return this.theimpossiblelibrary$right;
    }
    
    @Inject(at = @At("HEAD"), method = "drawGameInformation")
    private void theimpossiblelibrary$drawGameInformation(PoseStack matrix, CallbackInfo ci) {
        this.theimpossiblelibrary$left.clear();
        this.theimpossiblelibrary$left.addAll(this.getGameInformation());
        this.theimpossiblelibrary$right.clear();
        this.theimpossiblelibrary$right.addAll(this.getSystemInformation());
        RENDER_DEBUG_INFO.invoker().onRenderDebug(matrix,this.theimpossiblelibrary$left,this.theimpossiblelibrary$right);
    }
}