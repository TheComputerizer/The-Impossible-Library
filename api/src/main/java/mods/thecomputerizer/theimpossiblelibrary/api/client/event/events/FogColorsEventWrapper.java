package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_COLORS;

public abstract class FogColorsEventWrapper<E> extends ClientEventWrapper<E> {

    protected FogColorsEventWrapper() {
        super(FOG_COLORS);
    }

    @Override
    protected void populate() {

    }
}