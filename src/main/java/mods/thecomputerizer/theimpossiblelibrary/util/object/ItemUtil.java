package mods.thecomputerizer.theimpossiblelibrary.util.object;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ReportedException;

import java.util.Objects;

public class ItemUtil {

    public static NBTTagCompound getOrCreateTag(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        if(Objects.nonNull(tag)) return tag;
        tag = new NBTTagCompound();
        stack.setTagCompound(tag);
        return tag;
    }

    public static NBTTagCompound getOrOverrideTag(NBTTagCompound tag, String name) {
        try {
            return tag.getCompoundTag(name);
        } catch (ReportedException ex) {
            NBTTagCompound subTag = new NBTTagCompound();
            tag.setTag(name,subTag);
            return subTag;
        }
    }
}
