package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_PLAY_STREAMING;

public abstract class PlayStreamingSoundSourceEventWrapper<E> extends ClientEventWrapper<E> {

    protected PlayStreamingSoundSourceEventWrapper() {
        super(SOUND_PLAY_STREAMING);
    }

    @Override
    protected void populate() {

    }
}