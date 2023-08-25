package mods.thecomputerizer.theimpossiblelibrary.mixin.mixins;

import mods.thecomputerizer.theimpossiblelibrary.events.AdvancementEvents;
import mods.thecomputerizer.theimpossiblelibrary.network.packets.SendAdvancementEventPacket;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerAdvancements.class)
public class MixinPlayerAdvancements {

    @Shadow private ServerPlayer player;

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/Advancement;getRewards()Lnet/minecraft/advancements/AdvancementRewards;"), method = "award")
    private AdvancementRewards theimpossiblelibrary$onGranted(Advancement advancement) {
        AdvancementEvents.SERVER_GRANTED.invoker().register(this.player,advancement);
        new SendAdvancementEventPacket(advancement).addPlayers(this.player).send();
        return advancement.getRewards();
    }
}