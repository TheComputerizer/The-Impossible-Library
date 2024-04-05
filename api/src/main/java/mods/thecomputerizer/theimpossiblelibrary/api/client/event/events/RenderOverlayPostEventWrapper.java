package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.RenderOverlayEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_POST;

public abstract class RenderOverlayPostEventWrapper<E> extends RenderOverlayEventWrapper<E> {

    protected RenderOverlayPostEventWrapper() {
        super(RENDER_OVERLAY_POST);
    }

    @Override
    public void populate() {}
}
