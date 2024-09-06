package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.KEY_INPUT;

public abstract class InputKeyEventWrapper<E> extends ClientEventWrapper<E> {

    protected InputKeyEventWrapper() {
        super(KEY_INPUT);
    }
    
    public abstract boolean isKey(KeyAPI<?> key);

    @Override public void populate() {}
}