package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.File;
import java.io.IOException;

public class Tag1_12_2 implements TagAPI {

    @Override
    public CompoundTagAPI makeCompoundTag() {
        return new CompoundTag1_12_2(new NBTTagCompound());
    }

    @Override
    public ListTagAPI makeListTag() {
        return new ListTag1_12_2(new NBTTagList());
    }

    @Override
    public CompoundTagAPI readFromFile(File file) throws IOException {
        return new CompoundTag1_12_2(CompressedStreamTools.read(file));
    }

    @Override
    public void writeToFile(CompoundTagAPI tag, File file) throws IOException {
        CompressedStreamTools.write(((CompoundTag1_12_2)tag).tag,file);
    }
}
