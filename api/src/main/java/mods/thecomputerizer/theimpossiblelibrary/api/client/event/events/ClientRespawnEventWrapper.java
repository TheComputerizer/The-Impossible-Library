package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CLIENT_RESPAWN;

public abstract class ClientRespawnEventWrapper<E> extends ClientEventWrapper<E> {

    protected ClientRespawnEventWrapper() {
        super(CLIENT_RESPAWN);
    }

    @Override
    protected void populate() {

    }
}