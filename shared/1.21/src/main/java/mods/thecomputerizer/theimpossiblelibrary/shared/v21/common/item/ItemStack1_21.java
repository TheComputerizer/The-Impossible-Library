package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.component.CompoundComponent1_21;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static net.minecraft.core.component.DataComponents.CUSTOM_DATA;
import static net.minecraft.world.item.component.CustomData.EMPTY;

public class ItemStack1_21 extends ItemStackAPI<ItemStack> {

    public ItemStack1_21(Object stack) {
        super((ItemStack)stack);
    }

    @Override public int getCount() {
        return this.wrapped.getCount();
    }

    @Override public ItemAPI<?> getItem() {
        return WrapperHelper.wrapItem(this.wrapped.getItem());
    }

    @Override public CompoundTagAPI<?> getOrCreateTag() {
        this.wrapped.update(CUSTOM_DATA,EMPTY,data -> data);
        return new CompoundComponent1_21(this.wrapped.get(CUSTOM_DATA));
    }

    @Override public @Nullable CompoundTagAPI<?> getTag() {
        return this.wrapped.has(CUSTOM_DATA) ? new CompoundComponent1_21(this.wrapped.get(CUSTOM_DATA)) : null;
    }

    @Override public boolean isEmpty() {
        return this.wrapped.isEmpty();
    }

    @Override public void setCount(int count) {
        this.wrapped.setCount(count);
    }

    @Override public void setTag(@Nullable CompoundTagAPI<?> api) {
        if(Objects.isNull(api)) return;
        Object value = api.getWrapped();
        CompoundTag updateWith = value instanceof CompoundTag ? (CompoundTag)value : ((CustomData)value).copyTag();
        this.wrapped.update(CUSTOM_DATA,EMPTY,tag -> CustomData.of(tag.copyTag().merge(updateWith)));
    }
}