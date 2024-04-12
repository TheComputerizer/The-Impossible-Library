package mods.thecomputerizer.theimpossiblelibrary.api.common.sound;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;

@Getter
public abstract class SoundEventAPI<S> implements RegistryEntryAPI<S> {

    protected final S sound;

    protected SoundEventAPI(S sound) {
        this.sound = sound;
    }

    @Override
    public S getValue() {
        return this.sound;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends S> getValueClass() {
        return (Class<? extends S>)this.sound.getClass();
    }
}