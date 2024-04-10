package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.KEY_INPUT;

public abstract class InputKeyEventWrapper<E> extends ClientEventWrapper<E> { //TODO

    protected InputKeyEventWrapper() {
        super(KEY_INPUT);
    }

    @Override
    public void populate() {

    }
}