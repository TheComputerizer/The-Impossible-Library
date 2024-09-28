package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientEntityViewEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_DENSITY;

public abstract class FogDensityEventWrapper<E> extends ClientEntityViewEventType<E> {

    protected EventFieldWrapper<E,Float> density;

    protected FogDensityEventWrapper() {
        super(FOG_DENSITY);
    }

    public float getDensity() {
        return this.density.get(this.event);
    }

    @Override protected void populate() {
        super.populate();
        this.density = wrapDensityField();
    }

    protected abstract EventFieldWrapper<E,Float> wrapDensityField();
}