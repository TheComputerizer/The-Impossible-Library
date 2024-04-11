package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import net.minecraft.client.audio.ISound;

public class SoundHelper1_16_5 implements SoundHelperAPI<ISound> {

    @Override
    public SoundAPI<ISound> getAPI(ISound sound) {
        return new Sound1_16_5(sound);
    }
}