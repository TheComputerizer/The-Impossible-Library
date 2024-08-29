package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraft.util.SoundEvent;

public class SoundEvent1_16_5 extends SoundEventAPI<SoundEvent> {

    public SoundEvent1_16_5(SoundEvent sound) {
        super(sound);
    }

    @Override
    public ResourceLocation1_16_5 getRegistryName() {
        return new ResourceLocation1_16_5(this.sound.getRegistryName());
    }
}