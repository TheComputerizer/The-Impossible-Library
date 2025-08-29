package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.item.ItemStack1_20;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.component.CustomData;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static net.minecraft.core.component.DataComponents.CUSTOM_DATA;
import static net.minecraft.world.item.component.CustomData.EMPTY;

public class ItemStack1_20_6 extends ItemStack1_20 {

    public ItemStack1_20_6(Object stack) {
        super(stack);
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

    @Override public void setTag(@Nullable CompoundTagAPI<?> api) {
        if(Objects.isNull(this.wrapped)|| Objects.isNull(api)) return;
        Object value = api.getWrapped();
        CompoundTag updateWith = value instanceof CompoundTag ? (CompoundTag)value : ((CustomData)value).copyTag();
        this.wrapped.update(CUSTOM_DATA,EMPTY,tag -> CustomData.of(tag.copyTag().merge(updateWith)));
    }
}