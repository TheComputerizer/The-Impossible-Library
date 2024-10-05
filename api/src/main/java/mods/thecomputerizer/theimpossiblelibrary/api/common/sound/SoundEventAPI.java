package mods.thecomputerizer.theimpossiblelibrary.api.common.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

public abstract class SoundEventAPI<S> extends AbstractWrapped<S> implements RegistryEntryAPI<S> {

    protected SoundEventAPI(S sound) {
        super(sound);
    }
}