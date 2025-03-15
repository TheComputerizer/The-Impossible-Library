package mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.component.TagComponent1_21;
import net.minecraft.nbt.*;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Tag1_21 implements TagAPI {
    
    private final TagComponent1_21 componentDelegate = new TagComponent1_21();
    
    @SuppressWarnings("unchecked") @Override public <T> BaseTagAPI<T> getWrapped(T tag) {
        if(tag instanceof CompoundTag) return (BaseTagAPI<T>)new CompoundTag1_21((CompoundTag)tag);
        if(tag instanceof ListTag) return (BaseTagAPI<T>)new ListTag1_21((ListTag)tag);
        if(tag instanceof NumericTag) return (BaseTagAPI<T>)new PrimitiveTag1_21((NumericTag)tag);
        if(tag instanceof StringTag) return (BaseTagAPI<T>)new StringTag1_21((StringTag)tag);
        return this.componentDelegate.getWrapped(tag);
    }
    
    @Override public CompoundTag1_21 makeCompoundTag() {
        return new CompoundTag1_21(new CompoundTag());
    }

    @Override public ListTag1_21 makeListTag() {
        return new ListTag1_21(new ListTag());
    }
    
    @Override public PrimitiveTag1_21 makePrimitiveTag(boolean b) {
        return new PrimitiveTag1_21(ByteTag.valueOf(b));
    }
    
    @Override public PrimitiveTag1_21 makePrimitiveTag(byte b) {
        return new PrimitiveTag1_21(ByteTag.valueOf(b));
    }
    
    @Override public PrimitiveTag1_21 makePrimitiveTag(double d) {
        return new PrimitiveTag1_21(DoubleTag.valueOf(d));
    }
    
    @Override public PrimitiveTag1_21 makePrimitiveTag(float f) {
        return new PrimitiveTag1_21(FloatTag.valueOf(f));
    }
    
    @Override public PrimitiveTag1_21 makePrimitiveTag(int i) {
        return new PrimitiveTag1_21(IntTag.valueOf(i));
    }
    
    @Override public PrimitiveTag1_21 makePrimitiveTag(long l) {
        return new PrimitiveTag1_21(LongTag.valueOf(l));
    }
    
    @Override public PrimitiveTag1_21 makePrimitiveTag(short s) {
        return new PrimitiveTag1_21(ShortTag.valueOf(s));
    }
    
    @Override public StringTag1_21 makeStringTag(String value) {
        return new StringTag1_21(StringTag.valueOf(value));
    }
    
    @Override public CompoundTag1_21 readFromFile(File file) throws IOException {
        CompoundTag tag = null;
        try {
            tag = NbtIo.read(file.toPath());
        } catch(EOFException ex) {
            TILRef.logWarn("Empty data file {}",file.toPath(),ex.getMessage());
        }
        if(Objects.isNull(tag)) tag = new CompoundTag();
        return new CompoundTag1_21(tag);
    }

    @Override public void writeToFile(CompoundTagAPI<?> tag, File file) throws IOException {
        if(!tag.isEmpty()) NbtIo.write(tag.unwrap(),file.toPath());
    }
}