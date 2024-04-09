package mods.thecomputerizer.theimpossiblelibrary.legacy.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import net.minecraft.client.audio.ISound;

public class SoundHelperLegacy implements SoundHelperAPI<ISound> {

    @Override
    public SoundAPI<ISound> getAPI(ISound sound) {
        return new SoundLegacy(sound);
    }
}