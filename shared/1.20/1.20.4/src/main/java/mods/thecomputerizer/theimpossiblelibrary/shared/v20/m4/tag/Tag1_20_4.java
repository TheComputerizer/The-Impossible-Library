package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.tag.Tag1_20;
import net.minecraft.nbt.*;

import java.io.File;

public class Tag1_20_4 extends Tag1_20 {
    
    @Override public Object readFromFileDirect(File file) throws Exception {
        return NbtIo.read(file.toPath());
    }
    
    @Override public void writeToFileDirect(CompoundTagAPI<?> tag, File file) throws Exception {
        NbtIo.write(tag.unwrap(),file.toPath());
    }
}