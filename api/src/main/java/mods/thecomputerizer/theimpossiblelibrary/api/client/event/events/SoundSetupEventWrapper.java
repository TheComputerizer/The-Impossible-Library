package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_SETUP;

public abstract class SoundSetupEventWrapper<E> extends ClientEventWrapper<E> {

    protected SoundSetupEventWrapper() {
        super(SOUND_SETUP);
    }

    @Override
    protected void populate() {

    }
}