package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.RenderEntityViewEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOV_MODIFIER;

public abstract class FOVModifierEventWrapper<E> extends RenderEntityViewEventWrapper<E> {

    protected EventFieldWrapper<E,Float> fov;

    protected FOVModifierEventWrapper() {
        super(FOV_MODIFIER);
    }

    public float getFOV() {
        return this.fov.get(this.event);
    }

    @Override
    protected void populate() {
        super.populate();
        this.fov = wrapFOVField();
    }

    public void setFOV(float fov) {
        this.fov.set(this.event,fov);
    }

    protected abstract EventFieldWrapper<E,Float> wrapFOVField();
}