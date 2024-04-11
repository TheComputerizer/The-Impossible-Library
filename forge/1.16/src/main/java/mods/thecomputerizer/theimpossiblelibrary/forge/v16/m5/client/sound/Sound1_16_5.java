package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.ResourceLocation;

public class Sound1_16_5 implements SoundAPI<ISound> { //TODO implement this

    private final ISound sound;

    public Sound1_16_5(ISound sound) {
        this.sound = sound;
    }

    @Override
    public ResourceLocationAPI<ResourceLocation> getOggLocation() {
        return null;
    }

    @Override
    public ISound getSound() {
        return this.sound;
    }

    @Override
    public void play(float volume) {

    }
}
