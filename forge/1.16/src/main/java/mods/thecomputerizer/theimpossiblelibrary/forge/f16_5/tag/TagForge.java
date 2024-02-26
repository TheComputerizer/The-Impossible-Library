package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.ListNBT;

import java.io.File;
import java.io.IOException;

public class TagForge implements TagAPI {

    @Override
    public CompoundTagAPI makeCompoundTag() {
        return new CompoundTagForge(new CompoundNBT());
    }

    @Override
    public ListTagAPI makeListTag() {
        return new ListTagForge(new ListNBT());
    }

    @Override
    public CompoundTagAPI readFromFile(File file) throws IOException {
        return new CompoundTagForge(CompressedStreamTools.read(file));
    }

    @Override
    public void writeToFile(CompoundTagAPI tag, File file) throws IOException {
        CompressedStreamTools.write(((CompoundTagForge)tag).tag,file);
    }
}
