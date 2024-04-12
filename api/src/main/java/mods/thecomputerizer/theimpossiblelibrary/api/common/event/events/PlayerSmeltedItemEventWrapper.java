package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerStackEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ITEM_SMELTED;

public abstract class PlayerSmeltedItemEventWrapper<E> extends CommonPlayerStackEventType<E> {

    protected PlayerSmeltedItemEventWrapper() {
        super(PLAYER_ITEM_SMELTED);
    }

    public ItemStackAPI<?> getSmelting() {
        return this.stack.get(this.event);
    }
}