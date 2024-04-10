package mods.thecomputerizer.theimpossiblelibrary.legacy.item;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.RegistryEntryLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.RegistryLegacy;
import net.minecraft.item.Item;

public class ItemLegacy extends RegistryEntryLegacy<Item> implements ItemAPI<Item> {

    private final Item item;

    public ItemLegacy(Item item) {
        super(item);
        this.item = item;
    }

    @Override
    public RegistryEntryAPI<Item> getEntryAPI() {
        return this;
    }

    @Override
    public Item getItem() {
        return this.item;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RegistryLegacy<Item> getRegistry() {
        return (RegistryLegacy<Item>)(RegistryAPI<?>)RegistryHelper.getItemRegistry();
    }

    @Override
    public Class<? extends Item> getValueClass() {
        return this.item.getClass();
    }
}