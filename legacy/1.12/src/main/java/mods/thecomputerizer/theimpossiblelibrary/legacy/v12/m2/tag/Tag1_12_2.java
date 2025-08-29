package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import net.minecraft.nbt.*;

import java.io.File;

public class Tag1_12_2 implements TagAPI {
    
    @Override public <T> BaseTagAPI<T> getWrapped(T tag) {
        if(tag instanceof NBTTagCompound) return GenericUtils.cast(new CompoundTag1_12_2(tag));
        if(tag instanceof NBTTagList) return GenericUtils.cast(new ListTag1_12_2(tag));
        if(tag instanceof NBTPrimitive) return GenericUtils.cast(new PrimitiveTag1_12_2(tag));
        if(tag instanceof NBTTagString) return GenericUtils.cast(new StringTag1_12_2(tag));
        return null;
    }
    
    @Override public Object newCompoundTag() {
        return new NBTTagCompound();
    }
    
    @Override public Object newListTag() {
        return new NBTTagList();
    }
    
    @Override public Object newPrimitiveTag(boolean b) {
        return new NBTTagByte(b ? (byte)1 : (byte)0);
    }
    
    @Override public Object newPrimitiveTag(byte b) {
        return new NBTTagByte(b);
    }
    
    @Override public Object newPrimitiveTag(double d) {
        return new NBTTagDouble(d);
    }
    
    @Override public Object newPrimitiveTag(float f) {
        return new NBTTagFloat(f);
    }
    
    @Override public Object newPrimitiveTag(int i) {
        return new NBTTagInt(i);
    }
    
    @Override public Object newPrimitiveTag(long l) {
        return new NBTTagLong(l);
    }
    
    @Override public Object newPrimitiveTag(short s) {
        return new NBTTagShort(s);
    }
    
    @Override public Object newStringTag(String value) {
        return new NBTTagString(value);
    }
    
    @Override public Object readFromFileDirect(File file) throws Exception {
        return CompressedStreamTools.read(file);
    }

    @Override public void writeToFileDirect(CompoundTagAPI<?> tag, File file) throws Exception {
        CompressedStreamTools.write(tag.unwrap(),file);
    }
}
