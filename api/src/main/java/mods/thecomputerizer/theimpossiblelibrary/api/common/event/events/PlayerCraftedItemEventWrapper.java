package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerStackEventType;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ITEM_CRAFTED;

public abstract class PlayerCraftedItemEventWrapper<E> extends CommonPlayerStackEventType<E> {

    protected PlayerCraftedItemEventWrapper() {
        super(PLAYER_ITEM_CRAFTED);
    }
}