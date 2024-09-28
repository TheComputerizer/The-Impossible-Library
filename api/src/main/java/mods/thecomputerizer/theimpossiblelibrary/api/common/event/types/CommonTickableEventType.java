package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

@Getter
public abstract class CommonTickableEventType<E> extends CommonEventWrapper<E> {

    protected TickPhase tickPhase;
    protected TickType tickType;

    protected CommonTickableEventType(CommonType<?> type) {
        super(type);
    }

    public boolean isPhase(TickPhase phase) {
        return getTickPhase()==phase;
    }

    public boolean isType(TickType type) {
        return getTickType()==type;
    }

    @Override public void populate() {
        this.tickPhase = wrapTickPhase();
        this.tickType = wrapTickType();
    }

    protected abstract TickPhase wrapTickPhase();
    protected abstract TickType wrapTickType();

    public enum TickPhase { START, END, DEFAULT }
    public enum TickType { CLIENT, CUSTOM, PLAYER, RENDER, SERVER, WORLD }
}
