package mods.thecomputerizer.theimpossiblelibrary.api.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;

public interface SoundHelperAPI {

    float getCategoryVolume(String category);
    void play(SoundEventAPI<?> event);
    void setCategoryVolume(String category, float volume);
}