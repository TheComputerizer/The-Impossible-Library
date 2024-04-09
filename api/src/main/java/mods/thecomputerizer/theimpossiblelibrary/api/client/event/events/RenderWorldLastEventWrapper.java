package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientRenderEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_WORLD_LAST;

public abstract class RenderWorldLastEventWrapper<E> extends ClientRenderEventType<E> {

    protected RenderWorldLastEventWrapper() {
        super(RENDER_WORLD_LAST);
    }
}