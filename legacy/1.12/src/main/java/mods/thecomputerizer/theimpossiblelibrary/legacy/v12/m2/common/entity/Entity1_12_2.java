package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectInstanceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.EntityEntry;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

import static mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box.ZERO;
import static net.minecraftforge.fml.common.registry.ForgeRegistries.ENTITIES;

public class Entity1_12_2 extends EntityAPI<Entity,EntityEntry> {

    public static @Nullable EntityEntry getEntry(Entity entity) {
        ResourceLocation key = EntityList.getKey(entity);
        return Objects.nonNull(key) ? ENTITIES.getValue(key) : null;
    }

    public Entity1_12_2(Entity entity) {
        this(entity,getEntry(entity));
    }

    public Entity1_12_2(Entity entity, EntityEntry entry) {
        super(entity,entry);
    }

    @Override public Collection<EffectInstanceAPI<?>> getActiveEffects() {
        return this.entity instanceof EntityLivingBase ?
                ((EntityLivingBase)this.entity).getActivePotionEffects().stream().map(WrapperHelper::wrapEffectInstance)
                        .collect(Collectors.toList()) : Collections.emptyList();
    }

    @Override public Box getBoundingBox() {
        return Objects.nonNull(this.entity) ? getBoundingBox(this.entity.getEntityBoundingBox()) : ZERO;
    }

    public Box getBoundingBox(AxisAlignedBB aabb) {
        return new Box(aabb.minX,aabb.minY,aabb.minZ,aabb.maxX,aabb.maxY,aabb.maxZ);
    }

    @Override public DimensionAPI<?> getDimension() {
        return WrapperHelper.wrapDimension(getWorld(),DimensionManager.getProviderType(this.entity.dimension));
    }

    @Override public String getName() {
        return this.entity.getName();
    }

    @Override public BlockPosAPI<?> getPos() {
        return PosHelper.getPos(this.entity.getPosition());
    }

    @Override public EntityAPI<?,?> getRootVehicle() {
        return WrapperHelper.wrapEntity(this.entity.getLowestRidingEntity());
    }

    @Override public @Nullable EntityAPI<?,?> getVehicle() {
        Entity entity = this.entity.getRidingEntity();
        return Objects.nonNull(entity) ? WrapperHelper.wrapEntity(entity) : null;
    }

    @Override public WorldAPI<World> getWorld() {
        return WrapperHelper.wrapWorld(this.entity.world);
    }

    @Override public boolean isAnimal() {
        return this.entity instanceof EntityAnimal;
    }

    @Override public boolean isAlive() {
        return !this.entity.isDead;
    }

    @Override public boolean isLiving() {
        return this.entity instanceof EntityLivingBase;
    }

    @Override public boolean isPlayer() {
        return this.entity instanceof EntityPlayer;
    }

    @Override public boolean isOwnedBy(EntityAPI<?,?> owner) {
        return this.entity instanceof EntityTameable && ((EntityTameable)this.entity).getOwner()==owner.getEntity();
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