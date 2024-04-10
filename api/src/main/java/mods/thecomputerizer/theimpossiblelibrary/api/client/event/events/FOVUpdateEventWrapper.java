package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientPlayerEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOV_UPDATE;

public abstract class FOVUpdateEventWrapper<E> extends ClientPlayerEventType<E> {

    protected EventFieldWrapper<E,Float> fov;
    protected EventFieldWrapper<E,Float> newFOV;

    protected FOVUpdateEventWrapper() {
        super(FOV_UPDATE);
    }

    public float getFOV() {
        return this.fov.get(this.event);
    }

    public float getNewFOV() {
        return this.newFOV.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.fov = wrapFOVField();
        this.newFOV = wrapNewFOVField();
    }

    public void setNewFOV(float fov) {
        this.newFOV.set(this.event,fov);
    }

    protected abstract EventFieldWrapper<E,Float> wrapFOVField();
    protected abstract EventFieldWrapper<E,Float> wrapNewFOVField();
}