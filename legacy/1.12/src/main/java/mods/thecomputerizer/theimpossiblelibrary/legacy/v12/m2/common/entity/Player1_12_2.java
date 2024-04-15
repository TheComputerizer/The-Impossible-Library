package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectInstanceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.effect.EffectInstance1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.text.Text1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.Dimension1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.World1_12_2;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.EntityEntry;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class Player1_12_2<P extends EntityPlayer> extends PlayerAPI<P,EntityEntry> {

    protected Player1_12_2(P player) {
        super(player, Entity1_12_2.getEntry(player));
    }

    @Override
    public Collection<EffectInstanceAPI<?>> getActiveEffects() {
        return this.entity.getActivePotionEffects().stream().map(EffectInstance1_12_2::new).collect(Collectors.toList());
    }

    @Override
    public int getAir() {
        return this.entity.getAir();
    }

    /**
     * The bed location can be null, so I'm not sure why the compiler is complaining about it
     */
    @SuppressWarnings({"ConstantValue","UnreachableCode"})
    @Override
    public BlockPosAPI<?> getBedPos(DimensionAPI<?> dimension) {
        BlockPos pos = this.entity.getBedLocation(((Dimension1_12_2)dimension).getDimension().getId());
        return Objects.nonNull(pos) ? PosHelper.getPos(pos) : null;
    }

    @Override
    public Box getBoundingBox() {
        return Objects.nonNull(this.entity) ? getBoundingBox(this.entity.getEntityBoundingBox()) : Box.ZERO;
    }

    public Box getBoundingBox(AxisAlignedBB aabb) {
        return new Box(aabb.minX,aabb.minY,aabb.minZ,aabb.maxX,aabb.maxY,aabb.maxZ);
    }

    @Override
    public DimensionAPI<?> getDimension() {
        return WrapperHelper.wrapDimension(getWorld(), DimensionManager.getProviderType(this.entity.dimension));
    }

    @Override
    public String getName() {
        return this.entity.getName();
    }

    @Override
    public float getHealth() {
        return this.entity.getHealth();
    }

    @Override
    public float getMaxHealth() {
        return this.entity.getMaxHealth();
    }

    @Override
    public BlockPosAPI<?> getPos() {
        return PosHelper.getPos(this.entity.getPosition());
    }

    @Override
    public BlockPosAPI<?> getPosRounded() {
        return PosHelper.getPos(new BlockPos((int)(Math.round(this.entity.posX*2d)/2d),
                (int)(Math.round(this.entity.posY*2d)/2d),(int)(Math.round(this.entity.posZ*2d)/2d)));
    }

    @Override
    public ResourceLocationAPI<?> getRegistryName() {
        return new ResourceLocation1_12_2(this.type.getRegistryName());
    }

    @Override
    public EntityAPI<?,?> getRootVehicle() {
        return new Entity1_12_2(this.entity.getLowestRidingEntity());
    }

    @Override
    public UUID getUUID() {
        return this.entity.getUniqueID();
    }

    @Override
    public @Nullable EntityAPI<?,?> getVehicle() {
        Entity entity = this.entity.getRidingEntity();
        return Objects.nonNull(entity) ? new Entity1_12_2(entity) : null;
    }

    @Override
    public WorldAPI<?> getWorld() {
        return new World1_12_2(this.entity.world);
    }

    @Override
    public boolean isAlive() {
        return !this.entity.isDead;
    }

    @Override
    public boolean isAnimal() {
        return false;
    }

    @Override
    public boolean isFishing() {
        return Objects.nonNull(this.entity.fishEntity) && this.entity.fishEntity.isOverWater();
    }

    @Override
    public boolean isFlying() {
        return this.entity.isElytraFlying();
    }

    @Override
    public boolean isLiving() {
        return true;
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public boolean isOwnedBy(EntityAPI<?,?> owner) {
        return false;
    }

    @Override
    public void sendMessage(TextAPI<?> text, @Nullable UUID uuid) {
        if(Objects.nonNull(this.entity) && text instanceof Text1_12_2)
            this.entity.sendMessage(((Text1_12_2)text).getComponent());
    }

    @Override
    public void sendStatusMessage(TextAPI<?> text, boolean actionBar) {
        if(Objects.nonNull(this.entity) && text instanceof Text1_12_2)
            this.entity.sendStatusMessage(((Text1_12_2)text).getComponent(),actionBar);
    }

    @Override
    public double x() {
        return this.entity.posX;
    }

    @Override
    public double y() {
        return this.entity.posY;
    }

    @Override
    public double z() {
        return this.entity.posZ;
    }
}
