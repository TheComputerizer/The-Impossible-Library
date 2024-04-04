package mods.thecomputerizer.theimpossiblelibrary.api.common.event.item;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.RegistryEntryEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemAPI;

@Getter
public abstract class ItemEventWrapper<E,I> extends RegistryEntryEventWrapper<E> {

    protected ItemAPI<I> itemAPI;

    protected ItemEventWrapper(CommonType<?> type) {
        super(type);
    }

    protected void setItem(ItemAPI<I> api) {
        this.itemAPI = api;
        this.entryAPI = api.getEntryAPI();
    }
}