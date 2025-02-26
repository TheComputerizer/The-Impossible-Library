package mods.thecomputerizer.theimpossiblelibrary.shared.v19.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import net.minecraft.nbt.*;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Tag1_19 implements TagAPI {
    
    @SuppressWarnings("unchecked") @Override public <T> BaseTagAPI<T> getWrapped(T tag) {
        if(tag instanceof CompoundTag) return (BaseTagAPI<T>)new CompoundTag1_19((CompoundTag)tag);
        if(tag instanceof ListTag) return (BaseTagAPI<T>)new ListTag1_19((ListTag)tag);
        if(tag instanceof NumericTag) return (BaseTagAPI<T>)new PrimitiveTag1_19((NumericTag)tag);
        if(tag instanceof StringTag) return (BaseTagAPI<T>)new StringTag1_19((StringTag)tag);
        return null;
    }
    
    @Override public CompoundTag1_19 makeCompoundTag() {
        return new CompoundTag1_19(new CompoundTag());
    }

    @Override public ListTag1_19 makeListTag() {
        return new ListTag1_19(new ListTag());
    }
    
    @Override public PrimitiveTag1_19 makePrimitiveTag(boolean b) {
        return new PrimitiveTag1_19(ByteTag.valueOf(b));
    }
    
    @Override public PrimitiveTag1_19 makePrimitiveTag(byte b) {
        return new PrimitiveTag1_19(ByteTag.valueOf(b));
    }
    
    @Override public PrimitiveTag1_19 makePrimitiveTag(double d) {
        return new PrimitiveTag1_19(DoubleTag.valueOf(d));
    }
    
    @Override public PrimitiveTag1_19 makePrimitiveTag(float f) {
        return new PrimitiveTag1_19(FloatTag.valueOf(f));
    }
    
    @Override public PrimitiveTag1_19 makePrimitiveTag(int i) {
        return new PrimitiveTag1_19(IntTag.valueOf(i));
    }
    
    @Override public PrimitiveTag1_19 makePrimitiveTag(long l) {
        return new PrimitiveTag1_19(LongTag.valueOf(l));
    }
    
    @Override public PrimitiveTag1_19 makePrimitiveTag(short s) {
        return new PrimitiveTag1_19(ShortTag.valueOf(s));
    }
    
    @Override public StringTag1_19 makeStringTag(String value) {
        return new StringTag1_19(StringTag.valueOf(value));
    }
    
    @Override public CompoundTag1_19 readFromFile(File file) throws IOException {
        CompoundTag tag = null;
        try {
            tag = NbtIo.read(file);
        } catch(EOFException ex) {
            TILRef.logWarn("Empty data file {}",file.toPath(),ex.getMessage());
        }
        if(Objects.isNull(tag)) tag = new CompoundTag();
        return new CompoundTag1_19(tag);
    }

    @Override public void writeToFile(CompoundTagAPI<?> tag, File file) throws IOException {
        if(!tag.isEmpty()) NbtIo.write(tag.unwrap(),file);
    }
}