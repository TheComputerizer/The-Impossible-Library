package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.MOUSE_SCROLL;

public abstract class MouseScrollEventWrapper<E> extends ClientEventWrapper<E> {

    protected MouseScrollEventWrapper() {
        super(MOUSE_SCROLL);
    }

    @Override
    protected void populate() {

    }
}