package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_RESPAWN;

public abstract class PlayerRespawnEventWrapper<E> extends CommonPlayerEventType<E> {

    protected EventFieldWrapper<E,Boolean> endConquered;

    protected PlayerRespawnEventWrapper() {
        super(PLAYER_RESPAWN);
    }

    public boolean isEndConquered() {
        return this.endConquered.get(this.event);
    }

    @Override public void populate() {
        super.populate();
        this.endConquered = wrapEndConqueredField();
    }

    protected abstract EventFieldWrapper<E,Boolean> wrapEndConqueredField();
}