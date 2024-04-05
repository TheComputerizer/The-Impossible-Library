package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_BOSS;

public abstract class RenderOverlayBossEventWrapper<E> extends ClientEventWrapper<E> {

    protected RenderOverlayBossEventWrapper() {
        super(RENDER_OVERLAY_BOSS);
    }

    @Override
    protected void populate() {

    }
}