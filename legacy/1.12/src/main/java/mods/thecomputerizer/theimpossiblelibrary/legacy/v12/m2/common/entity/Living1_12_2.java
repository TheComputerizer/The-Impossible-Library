package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectInstanceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.effect.EffectInstance1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.World1_12_2;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.registry.EntityEntry;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class Living1_12_2 extends LivingEntityAPI<EntityLivingBase,EntityEntry> {

    public Living1_12_2(EntityLivingBase living) {
        this(living, Entity1_12_2.getEntry(living));
    }

    public Living1_12_2(EntityLivingBase living, EntityEntry entry) {
        super(living,entry);
    }

    @Override
    public Collection<EffectInstanceAPI<?>> getActiveEffects() {
        return this.entity.getActivePotionEffects().stream().map(EffectInstance1_12_2::new).collect(Collectors.toList());
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
        return this.entity instanceof EntityAnimal;
    }

    @Override
    public boolean isLiving() {
        return true;
    }

    @Override
    public boolean isPlayer() {
        return this.entity instanceof EntityPlayer;
    }

    @Override
    public boolean isOwnedBy(EntityAPI<?,?> owner) {
        return this.entity instanceof EntityTameable && ((EntityTameable)this.entity).getOwner()==owner.getEntity();
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
