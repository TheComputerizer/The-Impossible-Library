package mods.thecomputerizer.theimpossiblelibrary.api.client.event.types;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickType;

@Getter
public abstract class ClientTickableEventType<E> extends ClientEventWrapper<E> {

    protected TickPhase tickPhase;
    protected TickType tickType;

    protected ClientTickableEventType(ClientType<?> type) {
        super(type);
    }

    public boolean isPhase(TickPhase phase) {
        return getTickPhase()==phase;
    }

    public boolean isType(TickType type) {
        return getTickType()==type;
    }

    @Override
    protected void populate() {
        this.tickPhase = wrapTickPhase();
        this.tickType = wrapTickType();
    }

    protected abstract TickPhase wrapTickPhase();
    protected abstract TickType wrapTickType();
}
