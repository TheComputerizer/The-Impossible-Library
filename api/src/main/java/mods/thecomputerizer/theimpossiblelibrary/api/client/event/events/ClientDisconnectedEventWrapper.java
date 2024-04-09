package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientNetworkEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLIENT_DISCONNECTED;

public abstract class ClientDisconnectedEventWrapper<E> extends ClientNetworkEventType<E> {

    protected ClientDisconnectedEventWrapper() {
        super(CLIENT_DISCONNECTED);
    }
}