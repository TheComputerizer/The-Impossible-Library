package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.text.Text1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.Dimension1_12_2;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.DimensionManager;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

public class Player1_12_2 implements PlayerAPI<EntityPlayer> {

    private final LivingEntityAPI<EntityLivingBase> living;
    private final EntityPlayer player;

    public Player1_12_2(EntityPlayer player) {
        this.living = new Living1_12_2(player);
        this.player = player;
    }

    @Override
    public int getAir() {
        return this.player.getAir();
    }

    /**
     * The bed location can be null, so I'm not sure why the compiler is complaining about it
     */
    @SuppressWarnings({"ConstantValue","UnreachableCode"})
    @Override
    public BlockPosAPI<?> getBedPos(DimensionAPI<?> dimension) {
        BlockPos pos = this.player.getBedLocation(((Dimension1_12_2)dimension).getDimension().getId());
        return Objects.nonNull(pos) ? PosHelper.getPos(pos) : null;
    }

    @Override
    public DimensionAPI<?> getDimension() {
        return WrapperHelper.wrapDimension(DimensionManager.getProviderType(this.player.dimension));
    }

    @Override
    public EntityAPI<?> getEntityAPI() {
        return this.living.getEntityAPI();
    }

    @Override
    public LivingEntityAPI<EntityLivingBase> getLivingAPI() {
        return this.living;
    }

    @Override
    public EntityPlayer getPlayer() {
        return this.player;
    }

    @Override
    public Vector3d getPosExact() {
        return new Vector3d(this.player.posX,this.player.posY,this.player.posZ);
    }

    @Override
    public UUID getUUID() {
        return this.player.getUniqueID();
    }

    @Override
    public boolean isAdventureMode() { //TODO This is is network dependant
        return false;
    }

    @Override
    public boolean isClientPlayer() {
        return this.player instanceof EntityPlayerSP;
    }

    @Override
    public boolean isCreativeMode() {
        return this.player.isCreative();
    }

    @Override
    public boolean isFishing() {
        return Objects.nonNull(this.player.fishEntity) && this.player.fishEntity.isOverWater();
    }

    @Override
    public boolean isFlying() {
        return this.player.isElytraFlying();
    }

    @Override
    public boolean isSpectatorMode() {
        return this.player.isSpectator();
    }

    @Override
    public boolean isSurvivalMode() {
        return !isAdventureMode() && !isCreativeMode() && !isSpectatorMode();
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
