package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.util.Objects;

public class SoundEvent1_12_2 extends SoundEventAPI<SoundEvent> {

    public SoundEvent1_12_2(Object sound) {
        super(sound);
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        if(Objects.nonNull(this.wrapped)) this.wrapped.setRegistryName((ResourceLocation)registryName.unwrap());
    }
}