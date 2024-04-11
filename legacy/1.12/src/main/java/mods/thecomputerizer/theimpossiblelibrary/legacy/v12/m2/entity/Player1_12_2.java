package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.text.Text1_12_2;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.registry.EntityEntry;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

public class Player1_12_2 extends Living1_12_2 implements PlayerAPI<EntityPlayer> {

    private final EntityPlayer player;

    public Player1_12_2(EntityPlayer player) {
        this(player,getEntry(player));
    }

    public Player1_12_2(EntityPlayer player, EntityEntry entry) {
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
        if(Objects.nonNull(this.player) && text instanceof Text1_12_2)
            this.player.sendMessage(((Text1_12_2)text).getComponent());
    }

    @Override
    public void sendStatusMessage(TextAPI<?> text, boolean actionBar) {
        if(Objects.nonNull(this.player) && text instanceof Text1_12_2)
            this.player.sendStatusMessage(((Text1_12_2)text).getComponent(),actionBar);
    }
}
