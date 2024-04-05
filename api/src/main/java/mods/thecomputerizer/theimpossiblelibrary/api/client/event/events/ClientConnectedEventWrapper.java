package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLIENT_CONNECTED;

public abstract class ClientConnectedEventWrapper<E> extends ClientEventWrapper<E> {

    protected ClientConnectedEventWrapper() {
        super(CLIENT_CONNECTED);
    }

    @Override
    protected void populate() {

    }
}