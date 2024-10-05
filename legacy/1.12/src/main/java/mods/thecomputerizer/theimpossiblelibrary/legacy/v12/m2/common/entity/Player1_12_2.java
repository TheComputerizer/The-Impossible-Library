package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.container.PlayerInventoryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectInstanceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.EntityEntry;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class Player1_12_2<P extends EntityPlayer> extends PlayerAPI<P,EntityEntry> {

    protected Player1_12_2(P player) {
        super(player,(EntityEntry)Entity1_12_2.getEntry(player));
    }

    @Override public Collection<EffectInstanceAPI<?>> getActiveEffects() {
        return this.entity.getActivePotionEffects().stream().map(WrapperHelper::wrapEffectInstance).collect(Collectors.toList());
    }

    @Override public int getAir() {
        return this.entity.getAir();
    }

    /**
     * The bed location can be null, so I'm not sure why the compiler is complaining about it
     */
    @SuppressWarnings({"UnreachableCode","ConstantValue"})
    @Override public BlockPosAPI<?> getBedPos(DimensionAPI<?> dimension) {
        BlockPos pos = this.entity.getBedLocation(((DimensionType)dimension.unwrap()).getId());
        return Objects.nonNull(pos) ? WrapperHelper.wrapPosition(pos) : null;
    }

    @Override public Box getBoundingBox() {
        return Objects.nonNull(this.entity) ? getBoundingBox(this.entity.getEntityBoundingBox()) : Box.ZERO;
    }

    public Box getBoundingBox(AxisAlignedBB aabb) {
        return new Box(aabb.minX,aabb.minY,aabb.minZ,aabb.maxX,aabb.maxY,aabb.maxZ);
    }

    @Override public DimensionAPI<?> getDimension() {
        return WrapperHelper.wrapDimension(getWorld(),DimensionManager.getProviderType(this.entity.dimension));
    }

    @Override public float getHealth() {
        return this.entity.getHealth();
    }

    @Override public PlayerInventoryAPI<?> getInventory() {
        return WrapperHelper.wrapPlayerInventory(this.entity.inventory);
    }

    @Override public ItemStackAPI<?> getMainHandStack() {
        return WrapperHelper.wrapItemStack(this.entity.getHeldItemMainhand());
    }

    @Override public float getMaxHealth() {
        return this.entity.getMaxHealth();
    }

    @Override public String getName() {
        return this.entity.getName();
    }

    @Override public ItemStackAPI<?> getOffHandStack() {
        return WrapperHelper.wrapItemStack(this.entity.getHeldItemOffhand());
    }

    @Override public BlockPosAPI<?> getPos() {
        return WrapperHelper.wrapPosition(this.entity.getPosition());
    }

    @Override public EntityAPI<?,?> getRootVehicle() {
        return WrapperHelper.wrapEntity(this.entity.getLowestRidingEntity());
    }

    @Override public UUID getUUID() {
        return this.entity.getUniqueID();
    }

    @Override public @Nullable EntityAPI<?,?> getVehicle() {
        Entity entity = this.entity.getRidingEntity();
        return Objects.nonNull(entity) ? WrapperHelper.wrapEntity(entity) : null;
    }

    @Override public WorldAPI<?> getWorld() {
        return WrapperHelper.wrapWorld(this.entity.world);
    }

    @Override public boolean isAlive() {
        return !this.entity.isDead;
    }

    @Override public boolean isAnimal() {
        return false;
    }

    @Override public boolean isFishing() {
        return Objects.nonNull(this.entity.fishEntity) && this.entity.fishEntity.isOverWater();
    }

    @Override public boolean isFlying() {
        return this.entity.isElytraFlying();
    }

    @Override public boolean isLiving() {
        return true;
    }

    @Override public boolean isPlayer() {
        return true;
    }

    @Override public boolean isOwnedBy(EntityAPI<?,?> owner) {
        return false;
    }

    @Override public void sendMessage(TextAPI<?> text, @Nullable UUID uuid) {
        if(Objects.nonNull(this.entity)) this.entity.sendMessage(text.getAsComponent());
    }

    @Override public void sendStatusMessage(TextAPI<?> text, boolean actionBar) {
        if(Objects.nonNull(this.entity)) this.entity.sendStatusMessage(text.getAsComponent(),actionBar);
    }
    
    @Override public void setPosition(double x, double y, double z) {
        this.entity.setPosition(x,y,z);
    }

    @Override public double x() {
        return this.entity.posX;
    }

    @Override public double y() {
        return this.entity.posY;
    }

    @Override public double z() {
        return this.entity.posZ;
    }
}
