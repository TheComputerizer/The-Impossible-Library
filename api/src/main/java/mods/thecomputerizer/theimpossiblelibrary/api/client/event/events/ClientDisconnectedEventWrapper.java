package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLIENT_DISCONNECTED;

public abstract class ClientDisconnectedEventWrapper<E> extends ClientEventWrapper<E> {

    protected ClientDisconnectedEventWrapper() {
        super(CLIENT_DISCONNECTED);
    }

    @Override
    protected void populate() {

    }
}