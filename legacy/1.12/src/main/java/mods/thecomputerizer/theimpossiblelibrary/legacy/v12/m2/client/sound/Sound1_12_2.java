package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;

public class Sound1_12_2 extends SoundAPI<ISound> {

    public Sound1_12_2(ISound sound) {
        super(sound);
    }

    @Override
    public ResourceLocation1_12_2 getOggLocation() {
        return null;
    }

    @Override
    public void play(float volume) {
        Minecraft.getMinecraft().getSoundHandler().playSound(this.wrapped);
    }
}
