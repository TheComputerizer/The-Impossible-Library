package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import net.minecraft.nbt.*;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Tag1_16_5 implements TagAPI {
    
    @SuppressWarnings("unchecked") @Override public <T> BaseTagAPI<T> getWrapped(T tag) {
        if(tag instanceof CompoundNBT) return (BaseTagAPI<T>)new CompoundTag1_16_5((CompoundNBT)tag);
        if(tag instanceof ListNBT) return (BaseTagAPI<T>)new ListTag1_16_5((ListNBT)tag);
        if(tag instanceof NumberNBT) return (BaseTagAPI<T>)new PrimitiveTag1_16_5((NumberNBT)tag);
        if(tag instanceof StringNBT) return (BaseTagAPI<T>)new StringTag1_16_5((StringNBT)tag);
        return null;
    }
    
    @Override public CompoundTag1_16_5 makeCompoundTag() {
        return new CompoundTag1_16_5(new CompoundNBT());
    }

    @Override public ListTag1_16_5 makeListTag() {
        return new ListTag1_16_5(new ListNBT());
    }
    
    @Override public PrimitiveTag1_16_5 makePrimitiveTag(boolean b) {
        return new PrimitiveTag1_16_5(ByteNBT.valueOf(b));
    }
    
    @Override public PrimitiveTag1_16_5 makePrimitiveTag(byte b) {
        return new PrimitiveTag1_16_5(ByteNBT.valueOf(b));
    }
    
    @Override public PrimitiveTag1_16_5 makePrimitiveTag(double d) {
        return new PrimitiveTag1_16_5(DoubleNBT.valueOf(d));
    }
    
    @Override public PrimitiveTag1_16_5 makePrimitiveTag(float f) {
        return new PrimitiveTag1_16_5(FloatNBT.valueOf(f));
    }
    
    @Override public PrimitiveTag1_16_5 makePrimitiveTag(int i) {
        return new PrimitiveTag1_16_5(IntNBT.valueOf(i));
    }
    
    @Override public PrimitiveTag1_16_5 makePrimitiveTag(long l) {
        return new PrimitiveTag1_16_5(LongNBT.valueOf(l));
    }
    
    @Override public PrimitiveTag1_16_5 makePrimitiveTag(short s) {
        return new PrimitiveTag1_16_5(ShortNBT.valueOf(s));
    }
    
    @Override public StringTag1_16_5 makeStringTag(String value) {
        return new StringTag1_16_5(StringNBT.valueOf(value));
    }
    
    @Override public CompoundTag1_16_5 readFromFile(File file) throws IOException {
        CompoundNBT tag = null;
        try {
            tag = CompressedStreamTools.read(file);
        } catch(EOFException ex) {
            TILRef.logWarn("Empty data file {}",file.toPath(),ex.getMessage());
        }
        if(Objects.isNull(tag)) tag = new CompoundNBT();
        return new CompoundTag1_16_5(tag);
    }

    @Override public void writeToFile(CompoundTagAPI<?> tag, File file) throws IOException {
        if(!tag.isEmpty()) CompressedStreamTools.write(tag.unwrap(),file);
    }
}