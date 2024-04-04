package mods.thecomputerizer.theimpossiblelibrary.api.client.event.input;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

public interface InputEventAPI<M> extends ClientEventWrapper<M> {

    int getInput();
    boolean isScreenEvent();
}