package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.ITEM_TOOLTIP;

public abstract class ItemTooltipEventWrapper<E> extends ClientEventWrapper<E> {

    protected ItemTooltipEventWrapper() {
        super(ITEM_TOOLTIP);
    }

    @Override
    protected void populate() {

    }
}