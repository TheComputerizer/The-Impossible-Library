package mods.thecomputerizer.theimpossiblelibrary.api.common.entity;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectInstanceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

import static java.lang.Double.MAX_VALUE;

@Getter
public abstract class EntityAPI<E,V> extends AbstractWrapped<V> implements RegistryEntryAPI<V> {
    
    protected ResourceLocationAPI<?> registryName;

    protected E entity;

    protected EntityAPI(E entity, V type) {
        super(type);
        this.entity = entity;
    }
    
    @IndirectCallers public abstract boolean canTarget();
    
    @Override public boolean equals(Object other) {
        if(super.equals(other) && other instanceof EntityAPI<?,?>) {
            Object entity = getEntity();
            Object otherEntity = ((EntityAPI<?,?>)other).getEntity();
            if(Objects.isNull(entity)) return Objects.isNull(otherEntity);
            return Objects.nonNull(otherEntity) && entity.equals(otherEntity);
        }
        return false;
    }

    public abstract Collection<EffectInstanceAPI<?>> getActiveEffects();
    @IndirectCallers public abstract EntityAPI<?,?> getAttackTarget();
    public abstract Box getBoundingBox();
    public abstract CompoundTagAPI<?> getData();
    public abstract DimensionAPI<?> getDimension();

    @IndirectCallers
    public double getDistanceTo(EntityAPI<?,?> entity) {
        return Objects.nonNull(entity) ? getPos().distanceTo(entity.getPos()) : MAX_VALUE;
    }
    
    @IndirectCallers
    public double getDistanceTo(BlockPosAPI<?> pos) {
        return Objects.nonNull(pos) ? getPos().distanceTo(pos) : MAX_VALUE;
    }
    
    @IndirectCallers
    public double getDistanceTo(Vector3 pos) {
        return Objects.nonNull(pos) ? getPos().distanceTo(pos) : MAX_VALUE;
    }

    public abstract String getName();
    public abstract BlockPosAPI<?> getPos();

    public Vector3 getPosExact() {
        return new Vector3(x(),y(),z());
    }
    
    @IndirectCallers public BlockPosAPI<?> getPosRounded() {
        return PosHelper.getPos(Math.round(x()),Math.round(y()),Math.round(z()));
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        if(Objects.isNull(this.registryName) && Objects.nonNull(this.wrapped)) {
            RegistryAPI<?> registry = getRegistry();
            if(Objects.nonNull(registry)) this.registryName = registry.getKey(unwrap());
        }
        return this.registryName;
    }
    
    public abstract EntityAPI<?,?> getRootVehicle();
    public abstract @Nullable EntityAPI<?,?> getVehicle();
    public abstract WorldAPI<?> getWorld();
    @IndirectCallers public abstract boolean isAlive();
    @IndirectCallers public abstract boolean isAnimal();
    public abstract boolean isLiving();
    public abstract boolean isPlayer();
    @IndirectCallers public abstract boolean isOwnedBy(EntityAPI<?,?> owner);
    
    protected void setLocalRegistryName(ResourceLocationAPI<?> registryName) {
        this.registryName = registryName;
    }
    
    public void setPosition(BlockPosAPI<?> pos) {
        setPosition(pos.x(),pos.y(),pos.z());
    }
    
    public void setPosition(Vector3 vec) {
        setPosition(vec.dX(),vec.dY(),vec.dZ());
    }
    
    public void setPosition(int x, int y, int z) {
        setPosition((double)x,y,z);
    }
    
    public abstract void setPosition(double x, double y, double z);
    
    @SuppressWarnings("unchecked")
    public <T> T unwrapEntity() {
        return (T)getEntity();
    }
    
    public abstract double x();
    public abstract double y();
    public abstract double z();
}
