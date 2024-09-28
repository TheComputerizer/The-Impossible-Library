package mods.thecomputerizer.theimpossiblelibrary.api.server.event.types;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickType;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventWrapper;

@Getter
public abstract class ServerTickableEventType<E> extends ServerEventWrapper<E> {

    protected TickPhase tickPhase;
    protected TickType tickType;

    protected ServerTickableEventType(ServerType<?> type) {
        super(type);
    }

    public boolean isPhase(TickPhase phase) {
        return getTickPhase()==phase;
    }

    public boolean isType(TickType type) {
        return getTickType()==type;
    }

    @Override protected void populate() {
        this.tickPhase = wrapTickPhase();
        this.tickType = wrapTickType();
    }

    protected abstract TickPhase wrapTickPhase();
    protected abstract TickType wrapTickType();
}
