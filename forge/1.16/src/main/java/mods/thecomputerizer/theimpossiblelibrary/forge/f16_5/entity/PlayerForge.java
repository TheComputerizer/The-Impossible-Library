package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.text.TextForge;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;
import java.util.Objects;
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

    @Override
    public void sendMessage(TextAPI<?> text, @Nullable UUID uuid) {
        if(Objects.nonNull(this.player) && text instanceof TextForge)
            this.player.sendMessage(((TextForge)text).getComponent(),Objects.nonNull(uuid) ? uuid : this.player.getUUID());
    }

    @Override
    public void sendStatusMessage(TextAPI<?> text, boolean actionBar) {
        if(Objects.nonNull(this.player) && text instanceof TextForge)
            this.player.displayClientMessage(((TextForge)text).getComponent(),actionBar);
    }
}