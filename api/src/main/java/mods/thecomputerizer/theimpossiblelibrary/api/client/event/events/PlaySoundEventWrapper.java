package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientSoundSourceEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.SOUND_PLAY;

public abstract class PlaySoundEventWrapper<E> extends ClientSoundSourceEventType<E> {

    protected EventFieldWrapper<E,SoundAPI<?>> soundResult;

    protected PlaySoundEventWrapper() {
        super(SOUND_PLAY);
    }

    public SoundAPI<?> getSoundResult() {
        return this.soundResult.get(this.event);
    }

    @Override public void populate() {
        super.populate();
        this.soundResult = wrapSoundResultField();
    }

    public void setResult(SoundAPI<?> sound) {
        this.soundResult.set(this.event,sound);
    }

    protected abstract EventFieldWrapper<E,SoundAPI<?>> wrapSoundResultField();
}