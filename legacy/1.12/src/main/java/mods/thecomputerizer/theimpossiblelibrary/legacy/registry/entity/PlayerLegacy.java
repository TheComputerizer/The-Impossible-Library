package mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.PlayerAPI;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.registry.EntityEntry;

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
}
