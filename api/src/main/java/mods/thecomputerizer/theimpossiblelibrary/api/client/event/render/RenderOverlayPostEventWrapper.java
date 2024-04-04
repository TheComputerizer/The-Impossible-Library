package mods.thecomputerizer.theimpossiblelibrary.api.client.event.render;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_POST;

public abstract class RenderOverlayPostEventWrapper<E> extends RenderOverlayEventWrapper<E> {

    protected RenderOverlayPostEventWrapper() {
        super(RENDER_OVERLAY_POST);
    }
}
