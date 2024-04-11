package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.ResourceLocation;

public class Sound1_12_2 implements SoundAPI<ISound> {

    private final ISound sound;

    public Sound1_12_2(ISound sound) {
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
