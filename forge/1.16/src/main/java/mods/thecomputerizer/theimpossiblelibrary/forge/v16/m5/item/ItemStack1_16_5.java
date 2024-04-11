package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.item;

import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import net.minecraft.item.ItemStack;

public class ItemStack1_16_5 extends Item1_16_5 implements ItemStackAPI<ItemStack> {

    private final ItemStack stack;

    public ItemStack1_16_5(ItemStack stack) {
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
