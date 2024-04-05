package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ITEM_SMELTED;

public abstract class PlayerSmeltedItemEventWrapper<E> extends CommonEventWrapper<E> {

    protected PlayerSmeltedItemEventWrapper() {
        super(PLAYER_ITEM_SMELTED);
    }

    @Override
    protected void populate() {

    }
}