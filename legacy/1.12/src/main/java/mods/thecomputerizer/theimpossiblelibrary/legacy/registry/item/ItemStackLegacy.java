package mods.thecomputerizer.theimpossiblelibrary.legacy.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemStackAPI;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStackLegacy extends ItemLegacy implements ItemStackAPI<Item,ItemStack> {

    private final ItemStack stack;

    public ItemStackLegacy(ItemStack stack) {
        super(stack.getItem());
        this.stack = stack;
    }

    @Override
    public ItemStack getStack() {
        return this.stack;
    }
}
