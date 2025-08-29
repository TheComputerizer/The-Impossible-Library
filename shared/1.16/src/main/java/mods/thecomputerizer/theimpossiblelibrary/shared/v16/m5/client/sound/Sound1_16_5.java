package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;

public class Sound1_16_5 extends SoundAPI<SoundInstance> {
    
    public Sound1_16_5(Object sound) {
        super(sound);
    }

    @Override public ResourceLocationAPI<ResourceLocation> getOggLocation() { //TODO
        return null;
    }

    @Override public void play(float volume) {
        Minecraft.getInstance().getSoundManager().play(this.wrapped);
    }
}