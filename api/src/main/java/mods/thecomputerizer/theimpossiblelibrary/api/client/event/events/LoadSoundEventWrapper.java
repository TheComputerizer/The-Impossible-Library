package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientSoundEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_LOAD;

public abstract class LoadSoundEventWrapper<E> extends ClientSoundEventType<E> {

    protected LoadSoundEventWrapper() {
        super(SOUND_LOAD);
    }
}