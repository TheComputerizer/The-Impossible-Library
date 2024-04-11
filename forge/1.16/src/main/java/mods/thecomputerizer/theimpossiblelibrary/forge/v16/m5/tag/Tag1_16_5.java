package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.ListNBT;

import java.io.File;
import java.io.IOException;

public class Tag1_16_5 implements TagAPI {

    @Override
    public CompoundTagAPI makeCompoundTag() {
        return new CompoundTag1_16_5(new CompoundNBT());
    }

    @Override
    public ListTagAPI makeListTag() {
        return new ListTag1_16_5(new ListNBT());
    }

    @Override
    public CompoundTagAPI readFromFile(File file) throws IOException {
        return new CompoundTag1_16_5(CompressedStreamTools.read(file));
    }

    @Override
    public void writeToFile(CompoundTagAPI tag, File file) throws IOException {
        CompressedStreamTools.write(((CompoundTag1_16_5)tag).tag,file);
    }
}
