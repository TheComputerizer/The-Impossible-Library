package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import net.minecraft.nbt.*;

import java.io.File;
import java.io.IOException;

public class Tag1_12_2 implements TagAPI {
    
    @SuppressWarnings("unchecked") @Override public <T> BaseTagAPI<T> getWrapped(T tag) {
        if(tag instanceof NBTTagCompound) return (BaseTagAPI<T>)new CompoundTag1_12_2((NBTTagCompound)tag);
        if(tag instanceof NBTTagList) return (BaseTagAPI<T>)new ListTag1_12_2((NBTTagList)tag);
        if(tag instanceof NBTPrimitive) return (BaseTagAPI<T>)new PrimitiveTag1_12_2((NBTPrimitive)tag);
        if(tag instanceof NBTTagString) return (BaseTagAPI<T>)new StringTag1_12_2((NBTTagString)tag);
        return null;
    }

    @Override public CompoundTag1_12_2 makeCompoundTag() {
        return new CompoundTag1_12_2(new NBTTagCompound());
    }

    @Override public ListTag1_12_2 makeListTag() {
        return new ListTag1_12_2(new NBTTagList());
    }
    
    @Override public PrimitiveTag1_12_2 makePrimitiveTag(boolean b) {
        return new PrimitiveTag1_12_2(new NBTTagByte(b ? (byte)1 : 0));
    }
    
    @Override public PrimitiveTag1_12_2 makePrimitiveTag(byte b) {
        return new PrimitiveTag1_12_2(new NBTTagByte(b));
    }
    
    @Override public PrimitiveTag1_12_2 makePrimitiveTag(double d) {
        return new PrimitiveTag1_12_2(new NBTTagDouble(d));
    }
    
    @Override public PrimitiveTag1_12_2 makePrimitiveTag(float f) {
        return new PrimitiveTag1_12_2(new NBTTagFloat(f));
    }
    
    @Override public PrimitiveTag1_12_2 makePrimitiveTag(int i) {
        return new PrimitiveTag1_12_2(new NBTTagInt(i));
    }
    
    @Override public PrimitiveTag1_12_2 makePrimitiveTag(long l) {
        return new PrimitiveTag1_12_2(new NBTTagLong(l));
    }
    
    @Override public PrimitiveTag1_12_2 makePrimitiveTag(short s) {
        return new PrimitiveTag1_12_2(new NBTTagShort(s));
    }
    
    @Override public StringTag1_12_2 makeStringTag(String value) {
        return new StringTag1_12_2(new NBTTagString(value));
    }
    
    @Override public CompoundTag1_12_2 readFromFile(File file) throws IOException {
        return new CompoundTag1_12_2(CompressedStreamTools.read(file));
    }

    @Override public void writeToFile(CompoundTagAPI<?> tag, File file) throws IOException {
        CompressedStreamTools.write((NBTTagCompound)tag.getWrapped(),file);
    }
}
