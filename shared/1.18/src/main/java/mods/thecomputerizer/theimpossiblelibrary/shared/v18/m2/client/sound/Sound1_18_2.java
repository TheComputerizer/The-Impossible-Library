package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;

public class Sound1_18_2 extends SoundAPI<SoundInstance> {
    
    public Sound1_18_2(Object sound) {
        super((SoundInstance)sound);
    }

    @Override public ResourceLocationAPI<ResourceLocation> getOggLocation() { //TODO
        return null;
    }

    @Override public void play(float volume) {
        Minecraft.getInstance().getSoundManager().play(this.wrapped);
    }
}