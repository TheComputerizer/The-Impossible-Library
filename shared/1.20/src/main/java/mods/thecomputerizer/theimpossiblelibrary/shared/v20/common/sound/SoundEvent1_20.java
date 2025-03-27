package mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;

public class SoundEvent1_20 extends SoundEventAPI<SoundEvent> {

    public SoundEvent1_20(Object sound) {
        super(sound instanceof Holder<?> ? (SoundEvent)((Holder<?>)sound).value() : (SoundEvent)sound);
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName); //There is no built-in registryName field for forge in 1.19.+
    }
}