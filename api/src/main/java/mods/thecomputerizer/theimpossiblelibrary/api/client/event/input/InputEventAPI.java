package mods.thecomputerizer.theimpossiblelibrary.api.client.event.input;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventAPI;

public interface InputEventAPI<M> extends ClientEventAPI<M> {

    int getInput();
    boolean isScreenEvent();
}