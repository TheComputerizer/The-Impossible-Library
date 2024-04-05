package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.MOUSE_INPUT;

public abstract class InputMouseEventWrapper<E> extends ClientEventWrapper<E> {

    protected InputMouseEventWrapper() {
        super(MOUSE_INPUT);
    }

    @Override
    protected void populate() {

    }
}