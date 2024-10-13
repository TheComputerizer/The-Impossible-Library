package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.tag.CompoundTag1_18_2;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Objects;

public class ItemStack1_18_2 extends ItemStackAPI<ItemStack> {

    public ItemStack1_18_2(Object stack) {
        super((ItemStack)stack);
    }

    @Override public int getCount() {
        return this.wrapped.getCount();
    }

    @Override public ItemAPI<?> getItem() {
        return WrapperHelper.wrapItem(this.wrapped.getItem());
    }

    @Override public CompoundTag1_18_2 getOrCreateTag() {
        return new CompoundTag1_18_2(this.wrapped.getOrCreateTag());
    }

    @Override public @Nullable CompoundTag1_18_2 getTag() {
        CompoundTag tag = this.wrapped.getTag();
        return Objects.nonNull(tag) ? new CompoundTag1_18_2(tag) : null;
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