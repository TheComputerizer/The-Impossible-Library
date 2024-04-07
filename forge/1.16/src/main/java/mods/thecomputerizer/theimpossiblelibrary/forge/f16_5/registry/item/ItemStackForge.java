package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemStackAPI;
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
