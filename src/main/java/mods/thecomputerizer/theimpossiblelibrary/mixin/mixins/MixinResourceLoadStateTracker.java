package mods.thecomputerizer.theimpossiblelibrary.mixin.mixins;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import mods.thecomputerizer.theimpossiblelibrary.events.ResourcesLoadedEvent;
import mods.thecomputerizer.theimpossiblelibrary.mixin.access.IReloadStateAccess;
import mods.thecomputerizer.theimpossiblelibrary.util.file.LogUtil;
import net.minecraft.client.ResourceLoadStateTracker;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.util.Objects;

@Mixin(ResourceLoadStateTracker.class)
public class MixinResourceLoadStateTracker {

    @Unique
    private ResourceLoadStateTracker theimpossiblelibrary$cast() {
        return (ResourceLoadStateTracker)(Object)this;
    }

    @Inject(at = @At("TAIL"), method = "finishReload")
    private void theimpossiblelibrary$finishReload(CallbackInfo ci) {
        IReloadStateAccess reloadState = theimpossiblelibrary$getReloadState();
        if(Objects.isNull(reloadState)) Constants.testLog("RELOAD STATE WAS NULL. IS THIS A REFLECTION ERROR?");
        if(Objects.nonNull(reloadState) && reloadState.theimpossiblelibrary$isFinished())
            ResourcesLoadedEvent.EVENT.invoker().register();
    }

    @Unique
    private IReloadStateAccess theimpossiblelibrary$getReloadState() {
        try {
            Field field = ResourceLoadStateTracker.class.getDeclaredField("reloadState");
            Object reloadState = field.get(theimpossiblelibrary$cast());
            return reloadState instanceof IReloadStateAccess ? (IReloadStateAccess)reloadState : null;
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            LogUtil.logInternal(Level.ERROR,"Failed to get reload state! ResourcesLoadedEvent cannot be invoked!",ex);
            return null;
        }
    }
}