package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonEntityEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_ENTERING_CHUNK;

public abstract class EntityEnteringChunkEventWrapper<E> extends CommonEntityEventType<E> {

    protected EventFieldWrapper<E,Integer> newX;
    protected EventFieldWrapper<E,Integer> newZ;
    protected EventFieldWrapper<E,Integer> oldX;
    protected EventFieldWrapper<E,Integer> oldZ;

    protected EntityEnteringChunkEventWrapper() {
        super(ENTITY_ENTERING_CHUNK);
    }

    public int getNewX() {
        return this.newX.get(this.event);
    }

    public int getNewZ() {
        return this.newZ.get(this.event);
    }

    public int getOldX() {
        return this.oldX.get(this.event);
    }

    public int getOldZ() {
        return this.oldZ.get(this.event);
    }

    @Override public void populate() {
        super.populate();
        this.newX = wrapNewXField();
        this.newZ = wrapNewZField();
        this.oldX = wrapOldXField();
        this.oldZ = wrapOldZField();
    }

    public void setNewX(int x) {
        this.newX.set(this.event,x);
    }

    public void setNewZ(int z) {
        this.newZ.set(this.event,z);
    }

    public void setOldX(int x) {
        this.oldX.set(this.event,x);
    }

    public void setOldZ(int z) {
        this.oldZ.set(this.event,z);
    }

    protected abstract EventFieldWrapper<E,Integer> wrapNewXField();
    protected abstract EventFieldWrapper<E,Integer> wrapNewZField();
    protected abstract EventFieldWrapper<E,Integer> wrapOldXField();
    protected abstract EventFieldWrapper<E,Integer> wrapOldZField();
}
