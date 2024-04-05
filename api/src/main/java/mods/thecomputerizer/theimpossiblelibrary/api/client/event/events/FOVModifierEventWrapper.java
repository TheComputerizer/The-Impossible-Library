package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOV_MODIFIER;

public abstract class FOVModifierEventWrapper<E> extends ClientEventWrapper<E> {

    protected FOVModifierEventWrapper() {
        super(FOV_MODIFIER);
    }

    @Override
    protected void populate() {

    }
}