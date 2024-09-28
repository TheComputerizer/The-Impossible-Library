package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.REGISTER_MODELS;

public abstract class RegisterModelsEventWrapper<E> extends ClientEventWrapper<E> { //TODO Implement this

    protected RegisterModelsEventWrapper() {
        super(REGISTER_MODELS);
    }

    @Override protected void populate() {

    }
}