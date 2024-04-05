package mods.thecomputerizer.theimpossiblelibrary.api.client.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

public abstract class RenderEventWrapper<E> extends ClientEventWrapper<E> {

    protected RenderEventWrapper(ClientType<?> type) {
        super(type);
    }
}
