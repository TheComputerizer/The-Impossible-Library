package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonRegistryEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_ITEMS;

public abstract class RegisterItemsEventWrapper<E> extends CommonRegistryEventType<E,ItemAPI<?>> {

    protected RegisterItemsEventWrapper() {
        super(REGISTER_ITEMS);
    }
}
