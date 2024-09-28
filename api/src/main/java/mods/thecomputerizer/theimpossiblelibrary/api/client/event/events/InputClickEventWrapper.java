package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLICK_INPUT;

public abstract class InputClickEventWrapper<E> extends ClientEventWrapper<E> { //TODO

    protected InputClickEventWrapper() {
        super(CLICK_INPUT);
    }

    @Override protected void populate() {

    }
}