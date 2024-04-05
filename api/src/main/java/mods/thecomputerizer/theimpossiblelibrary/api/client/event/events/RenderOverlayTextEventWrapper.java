package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_TEXT;

public abstract class RenderOverlayTextEventWrapper<E> extends ClientEventWrapper<E> {

    protected RenderOverlayTextEventWrapper() {
        super(RENDER_OVERLAY_TEXT);
    }

    @Override
    protected void populate() {

    }
}