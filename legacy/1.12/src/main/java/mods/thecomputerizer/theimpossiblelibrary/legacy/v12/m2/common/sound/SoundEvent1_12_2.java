package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import net.minecraft.util.SoundEvent;

public class SoundEvent1_12_2 extends SoundEventAPI<SoundEvent> {

    public SoundEvent1_12_2(SoundEvent sound) {
        super(sound);
    }

    @Override
    public ResourceLocationAPI<?> getRegistryName() {
        return new ResourceLocation1_12_2(this.sound.getRegistryName());
    }
}