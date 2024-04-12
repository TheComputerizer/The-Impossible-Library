package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerEntityEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_STOP_TRACKING;

public abstract class PlayerStopTrackingEventWrapper<E> extends CommonPlayerEntityEventType<E> {

    protected PlayerStopTrackingEventWrapper() {
        super(PLAYER_STOP_TRACKING);
    }

    public EntityAPI<?,?> getTarget() {
        return this.entity.get(this.event);
    }
}