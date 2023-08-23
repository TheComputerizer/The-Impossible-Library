package mods.thecomputerizer.theimpossiblelibrary.mixin.mixins;

import mods.thecomputerizer.theimpossiblelibrary.events.ServerPlayerLoginEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public class MixinServerLevel {

    @Inject(at = @At("TAIL"), method = "addNewPlayer")
    private void theimpossiblelibrary$addNewPlayer(ServerPlayer player, CallbackInfo ci) {
        ServerLevel level;
        if(!player.isRemoved()) ServerPlayerLoginEvent.EVENT.invoker().register(player);
    }
}
