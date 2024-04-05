package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.TICK_RENDER;

public abstract class RenderTickEventWrapper<E> extends ClientEventWrapper<E> {

    protected RenderTickEventWrapper() {
        super(TICK_RENDER);
    }

    @Override
    protected void populate() {

    }
}