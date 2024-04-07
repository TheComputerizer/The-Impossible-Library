package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_BLOCK;

public abstract class RenderOverlayBlockEventWrapper<E> extends ClientEventWrapper<E> {

    protected RenderOverlayBlockEventWrapper() {
        super(RENDER_OVERLAY_BLOCK);
    }

    @Override
    protected void populate() {

    }
}