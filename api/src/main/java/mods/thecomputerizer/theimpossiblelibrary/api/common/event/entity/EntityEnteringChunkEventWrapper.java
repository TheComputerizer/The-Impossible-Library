package mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity;

import lombok.Getter;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_ENTERING_CHUNK;

@Getter
public abstract class EntityEnteringChunkEventWrapper<E> extends EntityEventWrapper<E> {

    protected int newX;
    protected int newZ;
    protected int oldX;
    protected int oldZ;

    protected EntityEnteringChunkEventWrapper() {
        super(ENTITY_ENTERING_CHUNK);
    }

    public abstract void setNewX(int x);
    public abstract void setNewZ(int z);
    public abstract void setOldX(int x);
    public abstract void setOldZ(int z);
}
