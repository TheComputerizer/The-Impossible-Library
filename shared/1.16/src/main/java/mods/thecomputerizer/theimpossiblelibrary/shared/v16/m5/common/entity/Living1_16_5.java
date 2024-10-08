package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectInstanceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class Living1_16_5 extends LivingEntityAPI<LivingEntity,EntityType<?>> {
    
    public Living1_16_5(Object living) {
        this((LivingEntity)living);
    }
    
    Living1_16_5(LivingEntity living) {
        super(living,living.getType());
    }
    
    @Override public Collection<EffectInstanceAPI<?>> getActiveEffects() {
        return this.entity.getActiveEffects().stream().map(WrapperHelper::wrapEffectInstance)
                .collect(Collectors.toList());
    }
    
    @Override public Box getBoundingBox() {
        return Objects.nonNull(this.entity) ? getBoundingBox(this.entity.getBoundingBox()) : null;
    }
    
    protected Box getBoundingBox(AxisAlignedBB box) {
        return new Box(box.minX,box.minY,box.minZ,box.maxX,box.maxY,box.maxZ);
    }

    @Override public DimensionAPI<?> getDimension() {
        return WrapperHelper.wrapDimension(getWorld(),this.entity.level.dimensionType());
    }
    
    @Override public float getHealth() {
        return this.entity.getHealth();
    }
    
    @Override public float getMaxHealth() {
        return this.entity.getMaxHealth();
    }
    
    @Override public String getName() {
        return this.entity.getName().getString();
    }
    
    @Override public BlockPosAPI<?> getPos() {
        return WrapperHelper.wrapPosition(this.entity.blockPosition());
    }
    
    @Override public EntityAPI<?,?> getRootVehicle() {
        return WrapperHelper.wrapEntity(this.entity.getRootVehicle());
    }
    
    @Override public @Nullable EntityAPI<?,?> getVehicle() {
        Entity entity = this.entity.getVehicle();
        return Objects.nonNull(entity) ? WrapperHelper.wrapEntity(entity) : null;
    }
    
    @Override public WorldAPI<?> getWorld() {
        return WrapperHelper.wrapWorld(this.entity.level);
    }
    
    @Override public boolean isAlive() {
        return this.entity.isAlive();
    }
    
    @Override public boolean isAnimal() {
        return this.entity instanceof AnimalEntity;
    }
    
    @Override public boolean isLiving() {
        return true;
    }
    
    @Override public boolean isPlayer() {
        return this.entity instanceof PlayerEntity;
    }
    
    @Override public boolean isOwnedBy(EntityAPI<?,?> owner) {
        return this.entity instanceof TameableEntity && ((TameableEntity)this.entity).getOwner()==this.entity;
    }
    
    @Override public void setPosition(double x, double y, double z) {
        this.entity.setPos(x,y,z);
    }
    
    @Override public double x() {
        return this.entity.position().x;
    }
    
    @Override public double y() {
        return this.entity.position().y;
    }
    
    @Override public double z() {
        return this.entity.position().z;
    }
}