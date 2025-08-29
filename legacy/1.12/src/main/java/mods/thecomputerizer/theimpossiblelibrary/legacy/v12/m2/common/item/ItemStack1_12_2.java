package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ItemStack1_12_2 extends ItemStackAPI<ItemStack> {

    public ItemStack1_12_2(Object stack) {
        super(stack);
    }

    @Override public int getCount() {
        return getIfNotNullOrDefault(ItemStack::getCount,0);
    }

    @Override public ItemAPI<?> getItem() {
        return getIfNotNull(w -> WrapperHelper.wrapItem(w.getItem()));
    }
    
    @Override public @Nullable CompoundTagAPI<?> getTag() {
        NBTTagCompound tag = getIfNotNull(ItemStack::getTagCompound);
        return Objects.nonNull(tag) ? TagHelper.getWrapped(tag).asCompoundTag() : null;
    }

    @Override public boolean isEmpty() {
        return getIfNotNullOrDefault(ItemStack::isEmpty,true);
    }

    @Override public void setCount(int count) {
        if(Objects.nonNull(this.wrapped)) this.wrapped.setCount(count);
    }

    @Override public void setTag(@Nullable CompoundTagAPI<?> tag) {
        if(Objects.nonNull(this.wrapped)) this.wrapped.setTagCompound(Objects.nonNull(tag) ? tag.unwrap() : null);
    }
}