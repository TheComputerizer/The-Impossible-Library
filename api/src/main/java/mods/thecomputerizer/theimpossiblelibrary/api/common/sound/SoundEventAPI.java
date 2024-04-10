package mods.thecomputerizer.theimpossiblelibrary.api.common.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;

public interface SoundEventAPI<S> {

    S getSoundEvent();
    RegistryEntryAPI<?> getEntryAPI();
}