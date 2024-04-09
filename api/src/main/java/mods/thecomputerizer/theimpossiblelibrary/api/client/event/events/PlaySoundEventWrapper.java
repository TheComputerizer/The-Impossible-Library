package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientSoundSourceEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_PLAY;

public abstract class PlaySoundEventWrapper<E,S> extends ClientSoundSourceEventType<E,S> {

    protected EventFieldWrapper<E,SoundAPI<S>> result;

    protected PlaySoundEventWrapper() {
        super(SOUND_PLAY);
    }

    public SoundAPI<S> getResult() {
        return this.result.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.result = wrapResultField();
    }

    public void setResult(SoundAPI<S> sound) {
        this.result.set(this.event,sound);
    }

    protected abstract EventFieldWrapper<E,SoundAPI<S>> wrapResultField();
}