package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

public class CompoundTag1_18_2 extends CompoundTagAPI<CompoundTag> {

    public CompoundTag1_18_2(CompoundTag tag) {
        super(tag);
    }
    
    @Override public CompoundTag1_18_2 asCompoundTag() {
        return this;
    }
    
    @Override public ListTag1_18_2 asListTag() {
        return null;
    }
    
    @Override public PrimitiveTag1_18_2 asPrimitiveTag() {
        return null;
    }
    
    @Override public StringTag1_18_2 asStringTag() {
        return null;
    }

    @Override public boolean contains(String key) {
        return this.wrapped.contains(key);
    }

    @Override public CompoundTag1_18_2 getCompoundTag(String key) {
        return new CompoundTag1_18_2(this.wrapped.getCompound(key));
    }

    @Override public ListTag1_18_2 getListTag(String key) {
        return (ListTag1_18_2)getTag(key).asListTag();
    }

    @Override public PrimitiveTag1_18_2 getPrimitiveTag(String key) {
        return (PrimitiveTag1_18_2)getTag(key).asPrimitiveTag();
    }

    @Override public String getString(String key) {
        return this.wrapped.getString(key);
    }

    @Override public BaseTagAPI<?> getTag(String key) {
        return TagHelper.getWrapped(this.wrapped.get(key));
    }
    
    @Override public boolean isCompound() {
        return true;
    }
    
    @Override public boolean isEmpty() {
        return this.wrapped.isEmpty();
    }
    
    @Override public boolean isList() {
        return false;
    }
    
    @Override public boolean isPrimitive() {
        return false;
    }
    
    @Override public boolean isString() {
        return false;
    }
    
    @Override public void putBoolean(String key, boolean b) {
        this.wrapped.putBoolean(key,b);
    }
    
    @Override public void putByte(String key, byte b) {
        this.wrapped.putByte(key,b);
    }
    
    @Override public void putDouble(String key, double d) {
        this.wrapped.putDouble(key,d);
    }
    
    @Override public void putFloat(String key, float f) {
        this.wrapped.putFloat(key,f);
    }
    
    @Override public void putInt(String key, int value) {
        this.wrapped.putInt(key,value);
    }
    
    @Override public void putLong(String key, long l) {
        this.wrapped.putLong(key,l);
    }
    
    @Override public void putShort(String key, short s) {
        this.wrapped.putShort(key,s);
    }
    
    @Override public void putString(String key, String value) {
        this.wrapped.putString(key,value);
    }

    @Override public void putTag(String key, BaseTagAPI<?> tag) {
        this.wrapped.put(key,(Tag)tag.getWrapped());
    }
}