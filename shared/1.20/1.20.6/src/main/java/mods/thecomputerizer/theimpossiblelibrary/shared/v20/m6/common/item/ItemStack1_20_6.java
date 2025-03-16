package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.item.ItemStack1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag.component.CompoundComponent1_20_6;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.component.CustomData;

import javax.annotation.Nullable;
import java.util.Objects;

import static net.minecraft.core.component.DataComponents.CUSTOM_DATA;
import static net.minecraft.world.item.component.CustomData.EMPTY;

public class ItemStack1_20_6 extends ItemStack1_20 {

    public ItemStack1_20_6(Object stack) {
        super(stack);
    }

    @Override public CompoundTagAPI<?> getOrCreateTag() {
        this.wrapped.update(CUSTOM_DATA,EMPTY,data -> data);
        return new CompoundComponent1_20_6(this.wrapped.get(CUSTOM_DATA));
    }

    @Override public @Nullable CompoundTagAPI<?> getTag() {
        return this.wrapped.has(CUSTOM_DATA) ? new CompoundComponent1_20_6(this.wrapped.get(CUSTOM_DATA)) : null;
    }

    @Override public void setTag(@Nullable CompoundTagAPI<?> api) {
        if(Objects.isNull(api)) return;
        Object value = api.getWrapped();
        CompoundTag updateWith = value instanceof CompoundTag ? (CompoundTag)value : ((CustomData)value).copyTag();
        this.wrapped.update(CUSTOM_DATA,EMPTY,tag -> CustomData.of(tag.copyTag().merge(updateWith)));
    }
}