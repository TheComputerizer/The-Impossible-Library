package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientTickableEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickType;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.TICK_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickType.CLIENT;

public abstract class ClientTickEventWrapper<E> extends ClientTickableEventType<E> {

    protected ClientTickEventWrapper() {
        super(TICK_CLIENT);
    }

    @Override
    protected TickType wrapTickType() {
        return CLIENT;
    }
}
