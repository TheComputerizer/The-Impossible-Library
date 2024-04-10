package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.item;

import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import net.minecraft.item.ItemStack;

public class ItemStackForge extends ItemForge implements ItemStackAPI<ItemStack> {

    private final ItemStack stack;

    public ItemStackForge(ItemStack stack) {
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
