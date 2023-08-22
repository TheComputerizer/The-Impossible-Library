package mods.thecomputerizer.theimpossiblelibrary.mixin.mixins;

import mods.thecomputerizer.theimpossiblelibrary.mixin.access.IReloadStateAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "net.minecraft.client.ResourceLoadStateTracker$ReloadState")
public class MixinReloadState implements IReloadStateAccess {

    @Shadow
    boolean finished;

    @Override
    public boolean theimpossiblelibrary$isFinished() {
        return this.finished;
    }
}
