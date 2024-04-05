package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_WORLD_LAST;

public abstract class RenderWorldLastEventWrapper<E> extends ClientEventWrapper<E> {

    protected RenderWorldLastEventWrapper() {
        super(RENDER_WORLD_LAST);
    }

    @Override
    protected void populate() {

    }
}