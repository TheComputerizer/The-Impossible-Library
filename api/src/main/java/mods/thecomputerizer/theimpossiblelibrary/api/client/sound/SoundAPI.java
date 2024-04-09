package mods.thecomputerizer.theimpossiblelibrary.api.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

public interface SoundAPI<S> {

    ResourceLocationAPI<?> getOggLocation();
    S getSound();
    void play(float volume);
}