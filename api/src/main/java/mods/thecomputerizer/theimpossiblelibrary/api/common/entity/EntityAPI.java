package mods.thecomputerizer.theimpossiblelibrary.api.common.entity;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectInstanceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import org.joml.Vector3d;
import org.joml.Vector3i;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

@SuppressWarnings("unused") @Getter
public abstract class EntityAPI<E,V> implements RegistryEntryAPI<V> {

    protected E entity;
    protected V type;

    protected EntityAPI(E entity, V type) {
        this.entity = entity;
        this.type = type;
    }

    public abstract Collection<? extends EffectInstanceAPI<?>> getActiveEffects();
    public abstract Box getBoundingBox();
    public abstract DimensionAPI<?> getDimension();

    public double getDistanceTo(EntityAPI<?,?> entity) {
        return Objects.nonNull(entity) ? getPos().distanceTo(entity.getPos()) : Double.MAX_VALUE;
    }

    public double getDistanceTo(BlockPosAPI<?> pos) {
        return Objects.nonNull(pos) ? getPos().distanceTo(pos) : Double.MAX_VALUE;
    }

    public double getDistanceTo(Vector3i pos) {
        return Objects.nonNull(pos) ? getPos().distanceTo(pos) : Double.MAX_VALUE;
    }

    public abstract String getName();
    public abstract BlockPosAPI<?> getPos();

    public Vector3d getPosExact() {
        return new Vector3d(x(),y(),z());
    }

    public abstract BlockPosAPI<?> getPosRounded();
    public abstract EntityAPI<?,?> getRootVehicle();

    @Override
    public V getValue() {
        return this.type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends V> getValueClass() {
        return (Class<? extends V>)this.type.getClass();
    }

    public abstract @Nullable EntityAPI<?,?> getVehicle();
    public abstract WorldAPI<?> getWorld();

    public abstract boolean isAlive();
    public abstract boolean isAnimal();
    public abstract boolean isLiving();
    public abstract boolean isPlayer();
    public abstract boolean isOwnedBy(EntityAPI<?,?> owner);
    
    public void setPosition(BlockPosAPI<?> pos) {
        setPosition(pos.x(),pos.y(),pos.z());
    }
    
    public void setPosition(Vector3i vec) {
        setPosition(vec.x,vec.y,vec.z);
    }
    
    public void setPosition(Vector3d vec) {
        setPosition(vec.x,vec.y,vec.z);
    }
    
    public void setPosition(int x, int y, int z) {
        setPosition((double)x,y,z);
    }
    
    public abstract void setPosition(double x, double y, double z);
    public abstract double x();
    public abstract double y();
    public abstract double z();
}
