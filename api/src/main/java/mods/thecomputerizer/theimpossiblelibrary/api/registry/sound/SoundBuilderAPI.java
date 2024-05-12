package mods.thecomputerizer.theimpossiblelibrary.api.registry.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryBuilder;

import javax.annotation.Nullable;

public abstract class SoundBuilderAPI extends RegistryEntryBuilder<SoundEventAPI<?>> {
    
    protected SoundBuilderAPI(@Nullable SoundBuilderAPI parent) {}
}