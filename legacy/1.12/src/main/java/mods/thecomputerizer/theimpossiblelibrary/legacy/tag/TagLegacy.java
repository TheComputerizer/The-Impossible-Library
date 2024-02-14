package mods.thecomputerizer.theimpossiblelibrary.legacy.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.io.File;
import java.io.IOException;

public class TagLegacy implements TagAPI {

    @Override
    public CompoundTagAPI makeCompoundTag() {
        return new CompoundTagLegacy(new NBTTagCompound());
    }

    @Override
    public ListTagAPI makeListTag() {
        return new ListTagLegacy(new NBTTagList());
    }

    @Override
    public CompoundTagAPI readFromFile(File file) throws IOException {
        return new CompoundTagLegacy(CompressedStreamTools.read(file));
    }

    @Override
    public void writeToFile(CompoundTagAPI tag, File file) throws IOException {
        CompressedStreamTools.write(((CompoundTagLegacy)tag).tag,file);
    }
}
