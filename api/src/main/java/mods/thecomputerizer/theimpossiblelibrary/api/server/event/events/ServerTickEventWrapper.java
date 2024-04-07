package mods.thecomputerizer.theimpossiblelibrary.api.server.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.TickableEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.TickableEventWrapper.TickType.SERVER;
import static mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventWrapper.ServerType.TICK_SERVER;

public abstract class ServerTickEventWrapper<E> extends ServerEventWrapper<E> implements TickableEventWrapper {

    protected ServerTickEventWrapper() {
        super(TICK_SERVER);
    }

    @Override
    public TickType getTickType() {
        return SERVER;
    }

    @Override
    protected void populate() {}
}
