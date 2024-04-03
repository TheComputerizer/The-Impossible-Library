package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemStackAPI;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStackForge extends ItemForge implements ItemStackAPI<Item,ItemStack> {

    private final ItemStack stack;

    public ItemStackForge(ItemStack stack) {
        super(stack.getItem());
        this.stack = stack;
    }

    @Override
    public ItemStack getStack() {
        return this.stack;
    }
}
