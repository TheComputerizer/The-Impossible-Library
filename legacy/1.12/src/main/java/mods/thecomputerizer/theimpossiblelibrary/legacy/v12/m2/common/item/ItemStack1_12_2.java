package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag.CompoundTag1_12_2;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;
import java.util.Objects;

public class ItemStack1_12_2 extends ItemStackAPI<ItemStack> {

    public ItemStack1_12_2(ItemStack stack) {
        super(stack);
    }

    @Override
    public int getCount() {
        return this.stack.getCount();
    }

    @Override
    public Item1_12_2 getItem() {
        return new Item1_12_2(this.stack.getItem());
    }

    @Override
    public @Nullable CompoundTag1_12_2 getTag() {
        NBTTagCompound tag = this.stack.getTagCompound();
        return Objects.nonNull(tag) ? new CompoundTag1_12_2(tag) : null;
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
    public void setTag(@Nullable CompoundTagAPI<?> tag) {
        this.stack.setTagCompound(Objects.nonNull(tag) ? (NBTTagCompound)tag.getWrapped() : null);
    }
}
