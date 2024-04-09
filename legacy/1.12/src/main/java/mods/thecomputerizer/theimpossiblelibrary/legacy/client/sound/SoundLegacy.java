package mods.thecomputerizer.theimpossiblelibrary.legacy.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.ResourceLocation;

public class SoundLegacy implements SoundAPI<ISound> {

    private final ISound sound;

    public SoundLegacy(ISound sound) {
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
