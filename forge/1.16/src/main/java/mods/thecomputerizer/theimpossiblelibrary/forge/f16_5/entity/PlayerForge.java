package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class PlayerForge extends LivingForge implements PlayerAPI<PlayerEntity> {

    private final PlayerEntity player;

    public PlayerForge(PlayerEntity player) {
        super(player);
        this.player = player;
    }

    @Override
    public LivingEntityAPI<LivingEntity> getLivingAPI() {
        return this;
    }

    @Override
    public PlayerEntity getPlayer() {
        return this.player;
    }

    @Override
    public UUID getUUID() {
        return this.player.getUUID();
    }

    @Override
    public boolean isClientPlayer() {
        return this.player instanceof ClientPlayerEntity;
    }
}
