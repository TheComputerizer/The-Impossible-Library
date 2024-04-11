package mods.thecomputerizer.theimpossiblelibrary.legacy.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.text.TextLegacy;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.registry.EntityEntry;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

public class PlayerLegacy extends LivingLegacy implements PlayerAPI<EntityPlayer> {

    private final EntityPlayer player;

    public PlayerLegacy(EntityPlayer player) {
        this(player,getEntry(player));
    }

    public PlayerLegacy(EntityPlayer player, EntityEntry entry) {
        super(player,entry);
        this.player = player;
    }

    @Override
    public LivingEntityAPI<EntityLivingBase> getLivingAPI() {
        return this;
    }

    @Override
    public EntityPlayer getPlayer() {
        return this.player;
    }

    @Override
    public UUID getUUID() {
        return this.player.getUniqueID();
    }

    @Override
    public boolean isClientPlayer() {
        return this.player instanceof EntityPlayerSP;
    }

    @Override
    public void sendMessage(TextAPI<?> text, @Nullable UUID uuid) {
        if(Objects.nonNull(this.player) && text instanceof TextLegacy)
            this.player.sendMessage(((TextLegacy)text).getComponent());
    }

    @Override
    public void sendStatusMessage(TextAPI<?> text, boolean actionBar) {
        if(Objects.nonNull(this.player) && text instanceof TextLegacy)
            this.player.sendStatusMessage(((TextLegacy)text).getComponent(),actionBar);
    }
}
