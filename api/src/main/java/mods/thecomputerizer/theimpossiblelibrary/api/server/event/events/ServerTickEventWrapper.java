package mods.thecomputerizer.theimpossiblelibrary.api.server.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickType;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.types.ServerTickableEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickType.SERVER;
import static mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventWrapper.ServerType.TICK_SERVER;

public abstract class ServerTickEventWrapper<E> extends ServerTickableEventType<E> {

    protected ServerTickEventWrapper() {
        super(TICK_SERVER);
    }

    @Override
    protected TickType wrapTickType() {
        return SERVER;
    }
}