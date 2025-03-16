package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag.component.TagComponent1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.tag.PrimitiveTag1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.tag.StringTag1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.tag.Tag1_20;
import net.minecraft.nbt.*;

import java.io.File;
import java.io.IOException;

public class Tag1_20_6 extends Tag1_20 {
    
    private final TagComponent1_20_6 componentDelegate = new TagComponent1_20_6();
    
    @SuppressWarnings("unchecked") @Override public <T> BaseTagAPI<T> getWrapped(T tag) {
        if(tag instanceof CompoundTag) return (BaseTagAPI<T>)new CompoundTag1_20_6((CompoundTag)tag);
        if(tag instanceof ListTag) return (BaseTagAPI<T>)new ListTag1_20_6((ListTag)tag);
        if(tag instanceof NumericTag) return (BaseTagAPI<T>)new PrimitiveTag1_20((NumericTag)tag);
        if(tag instanceof StringTag) return (BaseTagAPI<T>)new StringTag1_20((StringTag)tag);
        return this.componentDelegate.getWrapped(tag);
    }
    
    @Override public CompoundTag1_20_6 makeCompoundTag() {
        return new CompoundTag1_20_6(new CompoundTag());
    }
    
    @Override public ListTag1_20_6 makeListTag() {
        return new ListTag1_20_6(new ListTag());
    }
    
    @Override public PrimitiveTag1_20_6 makePrimitiveTag(boolean b) {
        return new PrimitiveTag1_20_6(ByteTag.valueOf(b));
    }
    
    @Override public PrimitiveTag1_20_6 makePrimitiveTag(byte b) {
        return new PrimitiveTag1_20_6(ByteTag.valueOf(b));
    }
    
    @Override public PrimitiveTag1_20_6 makePrimitiveTag(double d) {
        return new PrimitiveTag1_20_6(DoubleTag.valueOf(d));
    }
    
    @Override public PrimitiveTag1_20_6 makePrimitiveTag(float f) {
        return new PrimitiveTag1_20_6(FloatTag.valueOf(f));
    }
    
    @Override public PrimitiveTag1_20_6 makePrimitiveTag(int i) {
        return new PrimitiveTag1_20_6(IntTag.valueOf(i));
    }
    
    @Override public PrimitiveTag1_20_6 makePrimitiveTag(long l) {
        return new PrimitiveTag1_20_6(LongTag.valueOf(l));
    }
    
    @Override public PrimitiveTag1_20_6 makePrimitiveTag(short s) {
        return new PrimitiveTag1_20_6(ShortTag.valueOf(s));
    }
    
    @Override public StringTag1_20_6 makeStringTag(String value) {
        return new StringTag1_20_6(StringTag.valueOf(value));
    }
    
    @Override public CompoundTagAPI<?> readFromFile(File file) throws IOException {
        return this.componentDelegate.readFromFile(file);
    }
    
    @Override public void writeToFile(CompoundTagAPI<?> tag, File file) throws IOException {
        this.componentDelegate.writeToFile(tag,file);
    }
}