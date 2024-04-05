package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_LOAD;

public abstract class LoadSoundEventWrapper<E> extends ClientEventWrapper<E> {

    protected LoadSoundEventWrapper() {
        super(SOUND_LOAD);
    }

    @Override
    protected void populate() {

    }
}