package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundEvent1_16_5 extends SoundEventAPI<SoundEvent> {

    public SoundEvent1_16_5(Object sound) {
        super((SoundEvent)sound);
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        if(FORGE) this.wrapped.setRegistryName((ResourceLocation)registryName.unwrap());
    }
}