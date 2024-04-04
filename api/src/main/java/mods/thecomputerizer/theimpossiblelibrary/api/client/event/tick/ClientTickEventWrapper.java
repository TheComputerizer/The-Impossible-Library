package mods.thecomputerizer.theimpossiblelibrary.api.client.event.tick;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.tick.TickableEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.TICK_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.tick.TickableEventWrapper.TickType.CLIENT;

public abstract class ClientTickEventWrapper<E> extends ClientEventWrapper<E> implements TickableEventWrapper {

    protected ClientTickEventWrapper() {
        super(TICK_CLIENT);
    }

    @Override
    public TickType getTickType() {
        return CLIENT;
    }
}
