package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_PLAY;

public abstract class PlaySoundEventWrapper<E> extends ClientEventWrapper<E> {

    protected PlaySoundEventWrapper() {
        super(SOUND_PLAY);
    }

    @Override
    protected void populate() {

    }
}