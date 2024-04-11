package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.item;

import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import net.minecraft.item.ItemStack;

public class ItemStack1_12_2 extends Item1_12_2 implements ItemStackAPI<ItemStack> {

    private final ItemStack stack;

    public ItemStack1_12_2(ItemStack stack) {
        super(stack.getItem());
        this.stack = stack;
    }

    @Override
    public ItemAPI<?> getItemAPI() {
        return this;
    }

    @Override
    public ItemStack getStack() {
        return this.stack;
    }
}
