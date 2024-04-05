package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;

import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_ENTERING_CHUNK;

@Getter
public abstract class EntityEnteringChunkEventWrapper<E> extends CommonEventWrapper<E> {

    protected EntityAPI<?> entity;
    protected int newX;
    protected int newZ;
    protected int oldX;
    protected int oldZ;

    protected EntityEnteringChunkEventWrapper() {
        super(ENTITY_ENTERING_CHUNK);
    }

    protected abstract Function<E,?> getEntityFunc();

    @Override
    public void populate() {
        this.entity = WrapperHelper.wrapEntity(getEntityFunc().apply(this.event));
    }

    public abstract void setNewX(int x);
    public abstract void setNewZ(int z);
    public abstract void setOldX(int x);
    public abstract void setOldZ(int z);
}
