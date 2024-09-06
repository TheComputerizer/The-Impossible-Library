package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;

@SuppressWarnings("UnreachableCode")
public abstract class SoundHelper1_16_5<S> implements SoundHelperAPI<S> {

    @Override public SoundAPI<S> getAPI(S sound) {
        return WrapperHelper.wrapSoundInstance(sound);
    }
}