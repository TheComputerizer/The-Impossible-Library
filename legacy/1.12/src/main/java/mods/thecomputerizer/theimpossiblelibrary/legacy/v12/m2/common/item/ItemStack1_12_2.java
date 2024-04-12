package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
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
    public ItemAPI<?> getItem() {
        return new Item1_12_2(this.stack.getItem());
    }

    @Override
    public @Nullable CompoundTagAPI getTag() {
        NBTTagCompound tag = this.stack.getTagCompound();
        return Objects.nonNull(tag) ? new CompoundTag1_12_2(tag) : null;
    }

    @Override
    public void setTag(@Nullable CompoundTagAPI tag) {
        this.stack.setTagCompound(Objects.nonNull(tag) ? ((CompoundTag1_12_2)tag).getTag() : null);
    }
}
