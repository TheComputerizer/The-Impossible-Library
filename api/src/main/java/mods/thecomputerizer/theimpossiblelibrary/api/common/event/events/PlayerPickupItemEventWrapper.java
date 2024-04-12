package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerStackEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ITEM_PICKUP;

public abstract class PlayerPickupItemEventWrapper<E> extends CommonPlayerStackEventType<E> {

    protected EventFieldWrapper<E,EntityAPI<?,?>> entity;

    protected PlayerPickupItemEventWrapper() {
        super(PLAYER_ITEM_PICKUP);
    }

    public EntityAPI<?,?> getEntity() {
        return this.entity.get(this.event);
    }

    public ItemStackAPI<?> getStack() {
        return this.stack.get(this.event);
    }

    public void populate() {
        super.populate();
        this.entity = wrapEntityField();
    }

    protected abstract EventFieldWrapper<E,EntityAPI<?,?>> wrapEntityField();
}