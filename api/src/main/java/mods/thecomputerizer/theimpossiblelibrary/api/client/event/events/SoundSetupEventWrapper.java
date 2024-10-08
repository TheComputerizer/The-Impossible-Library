package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientSoundEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_SETUP;

public abstract class SoundSetupEventWrapper<E> extends ClientSoundEventType<E> {

    protected SoundSetupEventWrapper() {
        super(SOUND_SETUP);
    }
}