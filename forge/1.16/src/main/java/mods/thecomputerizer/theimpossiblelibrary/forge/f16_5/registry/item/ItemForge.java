package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.RegistryEntryForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.RegistryForge;
import net.minecraft.item.Item;

public class ItemForge extends RegistryEntryForge<Item> implements ItemAPI<Item> {

    private final Item item;

    public ItemForge(Item item) {
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
    protected RegistryForge<Item> getRegistry() {
        return (RegistryForge<Item>)(RegistryAPI<?>)RegistryHelper.getItemRegistry();
    }

    @Override
    public Class<? extends Item> getValueClass() {
        return this.item.getClass();
    }
}