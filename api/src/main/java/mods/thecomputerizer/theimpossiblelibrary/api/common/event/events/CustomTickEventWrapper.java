package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.CUSTOM_TICK;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickType.CUSTOM;

@Getter
public abstract class CustomTickEventWrapper<E> extends CommonTickableEventType<E> { //TODO

    protected CustomTick ticker;

    protected CustomTickEventWrapper() {
        super(CUSTOM_TICK);
    }

    @Override public void populate() {
        super.populate();
        this.ticker = wrapTicker();
    }

    protected abstract CustomTick wrapTicker();

    @Override protected TickPhase wrapTickPhase() {
        return DEFAULT;
    }

    @Override protected TickType wrapTickType() {
        return CUSTOM;
    }
}