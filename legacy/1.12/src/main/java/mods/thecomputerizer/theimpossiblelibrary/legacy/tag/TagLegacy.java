package mods.thecomputerizer.theimpossiblelibrary.legacy.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import net.minecraft.nbt.CompressedStreamTools;

import java.io.File;
import java.io.IOException;

public class TagLegacy implements TagAPI {

    @Override
    public CompoundTagAPI makeCompoundTag() {
        return null;
    }

    @Override
    public ListTagAPI makeListTag() {
        return null;
    }

    @Override
    public CompoundTagAPI readFromFile(File file) throws IOException {
        return null;
    }

    @Override
    public void writeToFile(CompoundTagAPI tag, File file) throws IOException {

    }
}
