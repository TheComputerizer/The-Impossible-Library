package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_PLAY_SOURCE;

public abstract class PlaySoundSourceEventWrapper<E> extends ClientEventWrapper<E> {

    protected PlaySoundSourceEventWrapper() {
        super(SOUND_PLAY_SOURCE);
    }

    @Override
    protected void populate() {

    }
}