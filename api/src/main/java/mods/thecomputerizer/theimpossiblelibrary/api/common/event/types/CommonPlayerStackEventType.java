package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemStackAPI;

public abstract class CommonPlayerStackEventType<E> extends CommonPlayerEventType<E> {

    protected EventFieldWrapper<E,ItemStackAPI<?>> crafting;

    protected CommonPlayerStackEventType(CommonType<?> type) {
        super(type);
    }

    public ItemStackAPI<?> getCrafting() {
        return this.crafting.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.crafting = wrapCraftingField();
    }

    protected abstract EventFieldWrapper<E,ItemStackAPI<?>> wrapCraftingField();
}
