package mods.thecomputerizer.theimpossiblelibrary.api.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

public abstract class SoundAPI<S> extends AbstractWrapped<S> {
    
    public SoundAPI(Object sound) {
        super(sound);
    }

    @IndirectCallers public abstract ResourceLocationAPI<?> getOggLocation();
    public abstract void play(float volume);
}