package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.ResourceLocation;

public class Sound1_16_5 extends SoundAPI<ISound> {

    public Sound1_16_5(ISound sound) {
        super(sound);
    }

    @Override
    public ResourceLocationAPI<ResourceLocation> getOggLocation() {
        return null;
    }

    @Override
    public void play(float volume) {
        Minecraft.getInstance().getSoundManager().play(this.wrapped);
    }
}
