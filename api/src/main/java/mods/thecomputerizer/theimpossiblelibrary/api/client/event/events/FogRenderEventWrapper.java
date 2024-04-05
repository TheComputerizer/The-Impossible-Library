package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_RENDER;

public abstract class FogRenderEventWrapper<E> extends ClientEventWrapper<E> {

    protected FogRenderEventWrapper() {
        super(FOG_RENDER);
    }

    @Override
    protected void populate() {

    }
}