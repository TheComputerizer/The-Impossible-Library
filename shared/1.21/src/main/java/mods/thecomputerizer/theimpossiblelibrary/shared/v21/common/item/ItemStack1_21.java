package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static net.minecraft.core.component.DataComponents.CUSTOM_DATA;
import static net.minecraft.world.item.component.CustomData.EMPTY;

public class ItemStack1_21 extends ItemStackAPI<ItemStack> {

    public ItemStack1_21(Object stack) {
        super(stack);
    }
    
    @Override public int getCount() {
        return getIfNotNullOrDefault(ItemStack::getCount,0);
    }
    
    @Override public ItemAPI<?> getItem() {
        return getIfNotNull(w -> WrapperHelper.wrapItem(w.getItem()));
    }
    
    @Override public CompoundTagAPI<?> getOrCreateTag() {
        if(Objects.nonNull(this.wrapped)) {
            this.wrapped.update(CUSTOM_DATA,EMPTY,data -> data);
            return TagHelper.getWrapped(this.wrapped.get(CUSTOM_DATA)).asCompoundTag();
        }
        return TagHelper.makeCompoundTag();
    }

    @Override public @Nullable CompoundTagAPI<?> getTag() {
        return Objects.nonNull(this.wrapped) && this.wrapped.has(CUSTOM_DATA) ?
                TagHelper.getWrapped(this.wrapped.get(CUSTOM_DATA)).asCompoundTag() : null;
    }
    
    @Override public boolean isEmpty() {
        return getIfNotNullOrDefault(ItemStack::isEmpty,true);
    }
    
    @Override public void setCount(int count) {
        if(Objects.nonNull(this.wrapped)) this.wrapped.setCount(count);
    }

    @Override public void setTag(@Nullable CompoundTagAPI<?> api) {
        if(Objects.isNull(this.wrapped)|| Objects.isNull(api)) return;
        Object value = api.getWrapped();
        CompoundTag updateWith = value instanceof CompoundTag ? (CompoundTag)value : ((CustomData)value).copyTag();
        this.wrapped.update(CUSTOM_DATA,EMPTY,tag -> CustomData.of(tag.copyTag().merge(updateWith)));
    }
}