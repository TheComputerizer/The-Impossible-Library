package mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.component.TagComponent1_21;
import net.minecraft.nbt.*;

import java.io.File;

public class Tag1_21 implements TagAPI {
    
    private final TagComponent1_21 componentDelegate = new TagComponent1_21();
    
    @Override public <T> BaseTagAPI<T> getWrapped(T tag) {
        if(tag instanceof CompoundTag) return GenericUtils.cast(new CompoundTag1_21(tag));
        if(tag instanceof ListTag) return GenericUtils.cast(new ListTag1_21(tag));
        if(tag instanceof NumericTag) return GenericUtils.cast(new PrimitiveTag1_21(tag));
        if(tag instanceof StringTag) return GenericUtils.cast(new StringTag1_21(tag));
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