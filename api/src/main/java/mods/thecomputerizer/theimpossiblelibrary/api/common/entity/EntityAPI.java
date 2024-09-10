package mods.thecomputerizer.theimpossiblelibrary.api.common.entity;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.effect.EffectInstanceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Box;
import mods.thecomputerizer.theimpossiblelibrary.api.util.AbstractWrapped;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.DimensionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import org.joml.Vector3d;
import org.joml.Vector3i;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;

import static java.lang.Double.MAX_VALUE;

@Getter
public abstract class EntityAPI<E,V> extends AbstractWrapped<V> implements RegistryEntryAPI<V> {

    protected E entity;

    protected EntityAPI(E entity, V type) {
        super(type);
        this.entity = entity;
    }

    public abstract Collection<EffectInstanceAPI<?>> getActiveEffects();
    public abstract Box getBoundingBox();
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
    public double getDistanceTo(Vector3i pos) {
        return Objects.nonNull(pos) ? getPos().distanceTo(pos) : MAX_VALUE;
    }

    public abstract String getName();
    public abstract BlockPosAPI<?> getPos();

    public Vector3d getPosExact() {
        return new Vector3d(x(),y(),z());
    }
    
    @IndirectCallers public BlockPosAPI<?> getPosRounded() {
        return PosHelper.getPos(Math.round(x()*2d)/2d,Math.round(y()*2d)/2d,Math.round(z()*2d)/2d);
    }
    public abstract EntityAPI<?,?> getRootVehicle();
    public abstract @Nullable EntityAPI<?,?> getVehicle();
    public abstract WorldAPI<?> getWorld();
    @IndirectCallers public abstract boolean isAlive();
    @IndirectCallers public abstract boolean isAnimal();
    public abstract boolean isLiving();
    public abstract boolean isPlayer();
    @IndirectCallers public abstract boolean isOwnedBy(EntityAPI<?,?> owner);
    
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
