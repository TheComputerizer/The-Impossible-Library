package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientSoundSourceEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_PLAY_SOURCE;

public abstract class PlaySoundSourceEventWrapper<E,S> extends ClientSoundSourceEventType<E,S> {

    protected EventFieldWrapper<E,String> uuid;

    protected PlaySoundSourceEventWrapper() {
        super(SOUND_PLAY_SOURCE);
    }

    public String getUUID() {
        return this.uuid.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.uuid = wrapUUIDField();
    }

    protected abstract EventFieldWrapper<E,String> wrapUUIDField();
}