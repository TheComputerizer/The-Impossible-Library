package mods.thecomputerizer.theimpossiblelibrary.shared.v20.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import net.minecraft.nbt.*;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Tag1_20 implements TagAPI {
    
    @SuppressWarnings("unchecked") @Override public <T> BaseTagAPI<T> getWrapped(T tag) {
        if(tag instanceof CompoundTag) return (BaseTagAPI<T>)new CompoundTag1_20((CompoundTag)tag);
        if(tag instanceof ListTag) return (BaseTagAPI<T>)new ListTag1_20((ListTag)tag);
        if(tag instanceof NumericTag) return (BaseTagAPI<T>)new PrimitiveTag1_20((NumericTag)tag);
        if(tag instanceof StringTag) return (BaseTagAPI<T>)new StringTag1_20((StringTag)tag);
        return null;
    }
    
    @Override public CompoundTag1_20 makeCompoundTag() {
        return new CompoundTag1_20(new CompoundTag());
    }

    @Override public ListTag1_20 makeListTag() {
        return new ListTag1_20(new ListTag());
    }
    
    @Override public PrimitiveTag1_20 makePrimitiveTag(boolean b) {
        return new PrimitiveTag1_20(ByteTag.valueOf(b));
    }
    
    @Override public PrimitiveTag1_20 makePrimitiveTag(byte b) {
        return new PrimitiveTag1_20(ByteTag.valueOf(b));
    }
    
    @Override public PrimitiveTag1_20 makePrimitiveTag(double d) {
        return new PrimitiveTag1_20(DoubleTag.valueOf(d));
    }
    
    @Override public PrimitiveTag1_20 makePrimitiveTag(float f) {
        return new PrimitiveTag1_20(FloatTag.valueOf(f));
    }
    
    @Override public PrimitiveTag1_20 makePrimitiveTag(int i) {
        return new PrimitiveTag1_20(IntTag.valueOf(i));
    }
    
    @Override public PrimitiveTag1_20 makePrimitiveTag(long l) {
        return new PrimitiveTag1_20(LongTag.valueOf(l));
    }
    
    @Override public PrimitiveTag1_20 makePrimitiveTag(short s) {
        return new PrimitiveTag1_20(ShortTag.valueOf(s));
    }
    
    @Override public StringTag1_20 makeStringTag(String value) {
        return new StringTag1_20(StringTag.valueOf(value));
    }
    
    @Override public CompoundTagAPI<?> readFromFile(File file) throws IOException {
        CompoundTag tag = null;
        try {
            tag = NbtIo.read(file);
        } catch(EOFException ex) {
            TILRef.logWarn("Empty data file {}",file.toPath(),ex.getMessage());
        }
        if(Objects.isNull(tag)) tag = new CompoundTag();
        return new CompoundTag1_20(tag);
    }

    @Override public void writeToFile(CompoundTagAPI<?> tag, File file) throws IOException {
        if(!tag.isEmpty()) NbtIo.write(tag.unwrap(),file);
    }
}