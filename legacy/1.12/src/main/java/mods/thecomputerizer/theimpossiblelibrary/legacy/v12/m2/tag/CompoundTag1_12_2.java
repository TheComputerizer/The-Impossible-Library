package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class CompoundTag1_12_2 extends CompoundTagAPI<NBTTagCompound> {

    public CompoundTag1_12_2(NBTTagCompound tag) {
        super(tag);
    }
    
    @Override public CompoundTagAPI<?> asCompoundTag() {
        return this;
    }
    
    @Override public ListTagAPI<?> asListTag() {
        return null;
    }
    
    @Override public PrimitiveTagAPI<?> asPrimitiveTag() {
        return null;
    }
    
    @Override public StringTagAPI<?> asStringTag() {
        return null;
    }
    
    @Override public boolean contains(String key) {
        return this.wrapped.hasKey(key);
    }

    @Override public CompoundTag1_12_2 getCompoundTag(String key) {
        return new CompoundTag1_12_2(this.wrapped.getCompoundTag(key));
    }

    @Override public ListTag1_12_2 getListTag(String key) {
        return (ListTag1_12_2)getTag(key).asListTag();
    }

    @Override public PrimitiveTag1_12_2 getPrimitiveTag(String key) {
        return (PrimitiveTag1_12_2)getTag(key).asPrimitiveTag();
    }

    @Override public String getString(String key) {
        return this.wrapped.getString(key);
    }

    @Override public BaseTagAPI<?> getTag(String key) {
        return TagHelper.getWrapped(this.wrapped.getTag(key));
    }
    
    @Override public boolean isCompound() {
        return true;
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
        this.wrapped.setBoolean(key,b);
    }
    
    @Override public void putByte(String key, byte b) {
        this.wrapped.setByte(key,b);
    }
    
    @Override public void putFloat(String key, float f) {
        this.wrapped.setFloat(key,f);
    }
    
    @Override public void putDouble(String key, double d) {
        this.wrapped.setDouble(key,d);
    }
    
    @Override public void putInt(String key, int value) {
        this.wrapped.setInteger(key,value);
    }
    
    @Override public void putLong(String key, long l) {
        this.wrapped.setLong(key,l);
    }
    
    @Override public void putShort(String key, short s) {
        this.wrapped.setShort(key,s);
    }
    
    @Override public void putString(String key, String value) {
        this.wrapped.setString(key,value);
    }

    @Override public void putTag(String key, BaseTagAPI<?> tag) {
        this.wrapped.setTag(key,(NBTBase)tag.getWrapped());
    }
}
