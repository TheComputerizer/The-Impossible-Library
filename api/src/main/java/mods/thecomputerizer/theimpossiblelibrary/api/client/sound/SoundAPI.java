package mods.thecomputerizer.theimpossiblelibrary.api.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.AbstractWrapped;

public abstract class SoundAPI<S> extends AbstractWrapped<S> {
    
    public SoundAPI(S sound) {
        super(sound);
    }

    public abstract ResourceLocationAPI<?> getOggLocation();
    public abstract void play(float volume);
}