package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.item;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.RegistryEntry1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.Registry1_16_5;
import net.minecraft.item.Item;

public class Item1_16_5 extends RegistryEntry1_16_5<Item> implements ItemAPI<Item> {

    private final Item item;

    public Item1_16_5(Item item) {
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
    protected Registry1_16_5<Item> getRegistry() {
        return (Registry1_16_5<Item>)(RegistryAPI<?>)RegistryHelper.getItemRegistry();
    }

    @Override
    public Class<? extends Item> getValueClass() {
        return this.item.getClass();
    }
}