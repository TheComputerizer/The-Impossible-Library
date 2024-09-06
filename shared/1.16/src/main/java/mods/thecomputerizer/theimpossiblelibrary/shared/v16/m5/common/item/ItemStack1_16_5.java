package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.tag.CompoundTag1_16_5;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nullable;
import java.util.Objects;

public class ItemStack1_16_5 extends ItemStackAPI<ItemStack> {

    public ItemStack1_16_5(Object stack) {
        super((ItemStack)stack);
    }

    @Override public int getCount() {
        return this.wrapped.getCount();
    }

    @Override public ItemAPI<?> getItem() {
        return WrapperHelper.wrapItem(this.wrapped.getItem());
    }

    @Override public CompoundTag1_16_5 getOrCreateTag() {
        return new CompoundTag1_16_5(this.wrapped.getOrCreateTag());
    }

    @Override public @Nullable CompoundTag1_16_5 getTag() {
        CompoundNBT tag = this.wrapped.getTag();
        return Objects.nonNull(tag) ? new CompoundTag1_16_5(tag) : null;
    }

    @Override public boolean isEmpty() {
        return this.wrapped.isEmpty();
    }

    @Override public void setCount(int count) {
        this.wrapped.setCount(count);
    }

    @Override public void setTag(@Nullable CompoundTagAPI<?> tag) {
        this.wrapped.setTag(Objects.nonNull(tag) ? tag.unwrap() : null);
    }
}