package mods.thecomputerizer.theimpossiblelibrary.api.client.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;

public abstract class ClientSoundSourceEventType<E,S> extends ClientSoundEventType<E,S> {

    protected EventFieldWrapper<E,String> name;
    protected EventFieldWrapper<E,SoundAPI<S>> sound;

    protected ClientSoundSourceEventType(ClientType<?> type) {
        super(type);
    }

    public String getName() {
        return this.name.get(this.event);
    }

    public SoundAPI<S> getSound() {
        return this.sound.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.name = wrapNameField();
        this.sound = wrapSoundField();
    }

    protected abstract EventFieldWrapper<E,String> wrapNameField();
    protected abstract EventFieldWrapper<E,SoundAPI<S>> wrapSoundField();
}
