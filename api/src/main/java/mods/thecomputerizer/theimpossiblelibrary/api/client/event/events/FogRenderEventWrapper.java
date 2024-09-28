package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientEntityViewEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_RENDER;

public abstract class FogRenderEventWrapper<E> extends ClientEntityViewEventType<E> {

    protected EventFieldWrapper<E,Float> farplane;
    protected EventFieldWrapper<E,Integer> fogMode;

    protected FogRenderEventWrapper() {
        super(FOG_RENDER);
    }

    public float getFarplane() {
        return this.farplane.get(this.event);
    }

    public int getFogMode() {
        return this.fogMode.get(this.event);
    }

    @Override protected void populate() {
        super.populate();
        this.farplane = wrapFarplaneField();
        this.fogMode = wrapFogModeField();
    }

    protected abstract EventFieldWrapper<E,Float> wrapFarplaneField();
    protected abstract EventFieldWrapper<E,Integer> wrapFogModeField();
}