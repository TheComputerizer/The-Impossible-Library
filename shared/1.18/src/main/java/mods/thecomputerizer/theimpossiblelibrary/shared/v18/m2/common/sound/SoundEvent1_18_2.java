package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class SoundEvent1_18_2 extends SoundEventAPI<SoundEvent> {

    public SoundEvent1_18_2(Object sound) {
        super(sound instanceof Holder<?> ? (SoundEvent)((Holder<?>)sound).value() : (SoundEvent)sound);
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        if(FORGE) this.wrapped.setRegistryName((ResourceLocation)registryName.unwrap());
    }
}