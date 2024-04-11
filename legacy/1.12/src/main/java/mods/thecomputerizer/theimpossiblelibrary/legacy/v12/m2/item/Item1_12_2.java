package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.item;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.RegistryEntry1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.Registry1_12_2;
import net.minecraft.item.Item;

public class Item1_12_2 extends RegistryEntry1_12_2<Item> implements ItemAPI<Item> {

    private final Item item;

    public Item1_12_2(Item item) {
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
    protected Registry1_12_2<Item> getRegistry() {
        return (Registry1_12_2<Item>)(RegistryAPI<?>)RegistryHelper.getItemRegistry();
    }

    @Override
    public Class<? extends Item> getValueClass() {
        return this.item.getClass();
    }
}