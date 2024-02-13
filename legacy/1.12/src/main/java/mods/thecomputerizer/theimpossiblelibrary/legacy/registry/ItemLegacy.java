package mods.thecomputerizer.theimpossiblelibrary.legacy.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import net.minecraft.item.Item;

public class ItemLegacy extends RegistryEntryLegacy<Item> implements ItemAPI<Item> {

    private final Item item;

    protected ItemLegacy(Item entry) {
        super(entry);
        this.item = entry;
    }

    @Override
    public Item getItem() {
        return this.item;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RegistryLegacy<Item> getRegistry() {
        RegistryAPI<?> api = RegistryHelper.getItemRegistry();
        return (RegistryLegacy<Item>)api;
    }
}