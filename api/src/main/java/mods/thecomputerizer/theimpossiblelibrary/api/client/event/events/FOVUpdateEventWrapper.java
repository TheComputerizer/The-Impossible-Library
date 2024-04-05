package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOV_UPDATE;

public abstract class FOVUpdateEventWrapper<E> extends ClientEventWrapper<E> {

    protected FOVUpdateEventWrapper() {
        super(FOV_UPDATE);
    }

    @Override
    protected void populate() {

    }
}