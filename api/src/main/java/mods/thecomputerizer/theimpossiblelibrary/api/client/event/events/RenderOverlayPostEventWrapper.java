package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_POST;

public abstract class RenderOverlayPostEventWrapper<E> extends ClientOverlayEventType<E> {

    protected RenderOverlayPostEventWrapper() {
        super(RENDER_OVERLAY_POST);
    }
}
