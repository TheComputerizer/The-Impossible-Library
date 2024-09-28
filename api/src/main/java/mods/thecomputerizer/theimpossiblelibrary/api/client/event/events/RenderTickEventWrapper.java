package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientTickableEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickType;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.TICK_RENDER;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickType.RENDER;

public abstract class RenderTickEventWrapper<E> extends ClientTickableEventType<E> {

    protected EventFieldWrapper<E,Float> tickTime;

    protected RenderTickEventWrapper() {
        super(TICK_RENDER);
    }

    public float getTickTime() {
        return this.tickTime.get(this.event);
    }

    @Override public void populate() {
        super.populate();
        this.tickTime = wrapTickTimeField();
    }

    @Override protected TickType wrapTickType() {
        return RENDER;
    }

    protected abstract EventFieldWrapper<E,Float> wrapTickTimeField();
}