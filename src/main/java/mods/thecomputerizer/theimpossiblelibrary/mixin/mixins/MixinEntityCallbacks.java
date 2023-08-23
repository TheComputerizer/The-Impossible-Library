package mods.thecomputerizer.theimpossiblelibrary.mixin.mixins;

import mods.thecomputerizer.theimpossiblelibrary.events.EntityAddedEvent;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.server.level.ServerLevel$EntityCallbacks")
public class MixinEntityCallbacks {

    @Inject(at = @At("TAIL"), method = "onTrackingStart(Lnet/minecraft/world/entity/Entity;)V")
    private void theimpossiblelibrary$onTrackingStart(Entity entity, CallbackInfo ci) {
        if(!entity.isRemoved()) EntityAddedEvent.EVENT.invoker().register(entity);
    }
}
