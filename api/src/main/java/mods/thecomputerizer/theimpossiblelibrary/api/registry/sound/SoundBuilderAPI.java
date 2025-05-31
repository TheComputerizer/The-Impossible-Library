package mods.thecomputerizer.theimpossiblelibrary.api.registry.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryBuilder;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import org.jetbrains.annotations.Nullable;


public abstract class SoundBuilderAPI extends RegistryEntryBuilder<SoundEventAPI<?>> {
    
    protected SoundBuilderAPI(@Nullable SoundBuilderAPI parent) {}
    
    @Override public SoundBuilderAPI setRegistryName(ResourceLocationAPI<?> name) {
        this.registryName = name;
        return this;
    }
}