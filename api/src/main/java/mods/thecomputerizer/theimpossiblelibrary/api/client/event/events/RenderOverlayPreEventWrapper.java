package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.RenderOverlayEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_PRE;

public abstract class RenderOverlayPreEventWrapper<E> extends RenderOverlayEventWrapper<E> {

    protected RenderOverlayPreEventWrapper() {
        super(RENDER_OVERLAY_PRE);
    }

    @Override
    public void populate() {}
}