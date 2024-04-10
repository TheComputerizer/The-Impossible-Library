package mods.thecomputerizer.theimpossiblelibrary.legacy.item;

import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.item.ItemStackAPI;
import net.minecraft.item.ItemStack;

public class ItemStackLegacy extends ItemLegacy implements ItemStackAPI<ItemStack> {

    private final ItemStack stack;

    public ItemStackLegacy(ItemStack stack) {
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
