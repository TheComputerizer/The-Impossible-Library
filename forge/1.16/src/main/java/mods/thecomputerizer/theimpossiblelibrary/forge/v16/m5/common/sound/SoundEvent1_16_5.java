package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraft.util.SoundEvent;

public class SoundEvent1_16_5 extends SoundEventAPI<SoundEvent> {

    public SoundEvent1_16_5(SoundEvent sound) {
        super(sound);
    }

    @Override
    public ResourceLocationAPI<?> getRegistryName() {
        return new ResourceLocation1_16_5(this.sound.getRegistryName());
    }
}