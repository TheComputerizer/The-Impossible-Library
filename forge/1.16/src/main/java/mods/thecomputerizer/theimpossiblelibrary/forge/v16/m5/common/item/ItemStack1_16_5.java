package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.tag.CompoundTag1_16_5;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nullable;
import java.util.Objects;

public class ItemStack1_16_5 extends ItemStackAPI<ItemStack> {

    public ItemStack1_16_5(ItemStack stack) {
        super(stack);
    }

    @Override
    public int getCount() {
        return this.stack.getCount();
    }

    @Override
    public ItemAPI<?> getItem() {
        return new Item1_16_5(this.stack.getItem());
    }

    @Override
    public CompoundTagAPI getOrCreateTag() {
        return new CompoundTag1_16_5(this.stack.getOrCreateTag());
    }

    @Override
    public @Nullable CompoundTagAPI getTag() {
        CompoundNBT tag = this.stack.getTag();
        return Objects.nonNull(tag) ? new CompoundTag1_16_5(tag) : null;
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public void setCount(int count) {
        this.stack.setCount(count);
    }

    @Override
    public void setTag(@Nullable CompoundTagAPI tag) {
        this.stack.setTag(Objects.nonNull(tag) ? ((CompoundTag1_16_5)tag).getTag() : null);
    }
}
