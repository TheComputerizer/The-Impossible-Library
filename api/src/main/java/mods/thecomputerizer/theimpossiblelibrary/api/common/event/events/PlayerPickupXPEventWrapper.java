package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerEntityEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_XP_PICKUP;

public abstract class PlayerPickupXPEventWrapper<E> extends CommonPlayerEntityEventType<E> {

    protected PlayerPickupXPEventWrapper() {
        super(PLAYER_XP_PICKUP);
    }

    public EntityAPI<?,?> getXPEntity() {
        return this.entity.get(this.event);
    }
}