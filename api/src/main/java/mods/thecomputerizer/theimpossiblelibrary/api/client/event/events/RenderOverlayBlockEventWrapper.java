package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_BLOCK;

public abstract class RenderOverlayBlockEventWrapper<E> extends ClientOverlayEventType<E> {

    protected RenderOverlayBlockEventWrapper() {
        super(RENDER_OVERLAY_BLOCK);
    }
}