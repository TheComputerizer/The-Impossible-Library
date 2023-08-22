package mods.thecomputerizer.theimpossiblelibrary.mixin.mixins;

import mods.thecomputerizer.theimpossiblelibrary.events.EntityAddedEvent;
import mods.thecomputerizer.theimpossiblelibrary.events.ServerPlayerLoginEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerLevel.class)
public class MixinServerLevel {

    @Inject(at = @At("RETURN"), method = "addEntity", cancellable = true)
    private void theimpossiblelibrary$addEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if(cir.getReturnValue()) cir.setReturnValue(EntityAddedEvent.EVENT.invoker().register(entity));
    }

    @Inject(at = @At("TAIL"), method = "addNewPlayer")
    private void theimpossiblelibrary$addEntity(ServerPlayer player, CallbackInfo ci) {
        if(!player.isRemoved()) ServerPlayerLoginEvent.EVENT.invoker().register(player);
    }
}
