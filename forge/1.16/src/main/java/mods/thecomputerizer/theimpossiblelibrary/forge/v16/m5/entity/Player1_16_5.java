package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.text.Text1_16_5;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

public class Player1_16_5 extends Living1_16_5 implements PlayerAPI<PlayerEntity> {

    private final PlayerEntity player;

    public Player1_16_5(PlayerEntity player) {
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
        if(Objects.nonNull(this.player) && text instanceof Text1_16_5)
            this.player.sendMessage(((Text1_16_5)text).getComponent(),Objects.nonNull(uuid) ? uuid : this.player.getUUID());
    }

    @Override
    public void sendStatusMessage(TextAPI<?> text, boolean actionBar) {
        if(Objects.nonNull(this.player) && text instanceof Text1_16_5)
            this.player.displayClientMessage(((Text1_16_5)text).getComponent(),actionBar);
    }
}