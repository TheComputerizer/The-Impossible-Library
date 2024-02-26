package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import net.minecraft.item.Item;

public class ItemForge extends RegistryEntryForge<Item> implements ItemAPI<Item> {

    private final Item item;

    protected ItemForge(Item entry) {
        super(entry);
        this.item = entry;
    }

    @Override
    public Item getItem() {
        return this.item;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected RegistryForge<Item> getRegistry() {
        RegistryAPI<?> api = RegistryHelper.getItemRegistry();
        return (RegistryForge<Item>)api;
    }
}