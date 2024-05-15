package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import net.minecraft.nbt.*;

import java.io.File;
import java.io.IOException;

public class Tag1_16_5 implements TagAPI {

    @Override
    public CompoundTag1_16_5 makeCompoundTag() {
        return new CompoundTag1_16_5(new CompoundNBT());
    }

    @Override
    public ListTag1_16_5 makeListTag() {
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
    
    @Override
    public CompoundTag1_16_5 readFromFile(File file) throws IOException {
        return new CompoundTag1_16_5(CompressedStreamTools.read(file));
    }

    @Override
    public void writeToFile(CompoundTagAPI<?> tag, File file) throws IOException {
        CompressedStreamTools.write((CompoundNBT)tag.getWrapped(),file);
    }
}
