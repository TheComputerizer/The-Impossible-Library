package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;

public class SoundEvent1_18_2 extends SoundEventAPI<SoundEvent> {

    public SoundEvent1_18_2(Object sound) {
        super(sound instanceof Holder<?> ? ((Holder<?>)sound).value() : sound);
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        setForgeRegistryName(this,registryName);
    }
}