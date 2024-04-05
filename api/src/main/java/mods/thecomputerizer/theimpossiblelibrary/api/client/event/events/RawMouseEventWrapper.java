package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.MOUSE_RAW;

public abstract class RawMouseEventWrapper<E> extends ClientEventWrapper<E> {

    protected RawMouseEventWrapper() {
        super(MOUSE_RAW);
    }

    @Override
    protected void populate() {

    }
}