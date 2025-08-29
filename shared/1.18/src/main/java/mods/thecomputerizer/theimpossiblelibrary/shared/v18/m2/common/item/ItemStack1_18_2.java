package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ItemStack1_18_2 extends ItemStackAPI<ItemStack> {

    public ItemStack1_18_2(Object stack) {
        super(stack);
    }
    
    @Override public int getCount() {
        return getIfNotNullOrDefault(ItemStack::getCount,0);
    }
    
    @Override public ItemAPI<?> getItem() {
        return getIfNotNull(w -> WrapperHelper.wrapItem(w.getItem()));
    }
    
    @Override public CompoundTagAPI<?> getOrCreateTag() {
        CompoundTag tag = getIfNotNullOrDefault(ItemStack::getOrCreateTag,new CompoundTag());
        return TagHelper.getWrapped(tag).asCompoundTag();
    }
    
    @Override public @Nullable CompoundTagAPI<?> getTag() {
        CompoundTag tag = getIfNotNull(ItemStack::getTag);
        return Objects.nonNull(tag) ? TagHelper.getWrapped(tag).asCompoundTag() : null;
    }
    
    @Override public boolean isEmpty() {
        return getIfNotNullOrDefault(ItemStack::isEmpty,true);
    }
    
    @Override public void setCount(int count) {
        if(Objects.nonNull(this.wrapped)) this.wrapped.setCount(count);
    }
    
    @Override public void setTag(@Nullable CompoundTagAPI<?> tag) {
        if(Objects.nonNull(this.wrapped)) this.wrapped.setTag(Objects.nonNull(tag) ? tag.unwrap() : null);
    }
}