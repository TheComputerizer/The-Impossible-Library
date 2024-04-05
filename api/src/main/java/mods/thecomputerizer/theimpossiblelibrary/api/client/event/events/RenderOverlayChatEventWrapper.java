package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_CHAT;

public abstract class RenderOverlayChatEventWrapper<E> extends ClientEventWrapper<E> {

    protected RenderOverlayChatEventWrapper() {
        super(RENDER_OVERLAY_CHAT);
    }

    @Override
    protected void populate() {

    }
}