package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_DENSITY;

public abstract class FogDensityEventWrapper<E> extends ClientEventWrapper<E> {

    protected FogDensityEventWrapper() {
        super(FOG_DENSITY);
    }

    @Override
    protected void populate() {

    }
}