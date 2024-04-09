package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import net.minecraft.client.audio.ISound;

public class SoundHelperForge implements SoundHelperAPI<ISound> {

    @Override
    public SoundAPI<ISound> getAPI(ISound sound) {
        return new SoundForge(sound);
    }
}