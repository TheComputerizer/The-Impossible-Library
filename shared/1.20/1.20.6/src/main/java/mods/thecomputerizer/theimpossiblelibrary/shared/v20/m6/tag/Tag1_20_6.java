package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag.component.TagComponent1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.tag.Tag1_20;
import net.minecraft.nbt.*;

import java.io.File;

public class Tag1_20_6 extends Tag1_20 {
    
    private final TagComponent1_20_6 componentDelegate = new TagComponent1_20_6();
    
    @Override public <T> BaseTagAPI<T> getWrapped(T tag) {
        if(tag instanceof CompoundTag) return GenericUtils.cast(new CompoundTag1_20_6(tag));
        if(tag instanceof ListTag) return GenericUtils.cast(new ListTag1_20_6(tag));
        if(tag instanceof NumericTag) return GenericUtils.cast(new PrimitiveTag1_20_6(tag));
        if(tag instanceof StringTag) return GenericUtils.cast(new StringTag1_20_6(tag));
        return this.componentDelegate.getWrapped(tag);
    }
    
    @Override public Object newCompoundTag() {
        return new CompoundTag();
    }
    
    @Override public Object newListTag() {
        return new ListTag();
    }
    
    @Override public Object newPrimitiveTag(boolean b) {
        return ByteTag.valueOf(b);
    }
    
    @Override public Object newPrimitiveTag(byte b) {
        return ByteTag.valueOf(b);
    }
    
    @Override public Object newPrimitiveTag(double d) {
        return DoubleTag.valueOf(d);
    }
    
    @Override public Object newPrimitiveTag(float f) {
        return FloatTag.valueOf(f);
    }
    
    @Override public Object newPrimitiveTag(int i) {
        return IntTag.valueOf(i);
    }
    
    @Override public Object newPrimitiveTag(long l) {
        return LongTag.valueOf(l);
    }
    
    @Override public Object newPrimitiveTag(short s) {
        return ShortTag.valueOf(s);
    }
    
    @Override public Object newStringTag(String value) {
        return StringTag.valueOf(value);
    }
    
    @Override public Object readFromFileDirect(File file) throws Exception {
        return this.componentDelegate.readFromFileDirect(file);
    }
    
    @Override public void writeToFileDirect(CompoundTagAPI<?> tag, File file) throws Exception {
        if(tag instanceof TagWrapper) NbtIo.write(tag.unwrap(),file.toPath());
        else this.componentDelegate.writeToFileDirect(tag,file);
    }
}