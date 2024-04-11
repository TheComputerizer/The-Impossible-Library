package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import net.minecraft.client.audio.ISound;

public class SoundHelper1_12_2 implements SoundHelperAPI<ISound> {

    @Override
    public SoundAPI<ISound> getAPI(ISound sound) {
        return new Sound1_12_2(sound);
    }
}