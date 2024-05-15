package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;

public class CompoundTag1_16_5 extends CompoundTagAPI<CompoundNBT> {

    public CompoundTag1_16_5(CompoundNBT tag) {
        super(tag);
    }
    
    @Override
    public CompoundTag1_16_5 asCompoundTag() {
        return this;
    }
    
    @Override
    public ListTag1_16_5 asListTag() {
        return null;
    }
    
    @Override
    public PrimitiveTag1_16_5 asPrimitiveTag() {
        return null;
    }
    
    @Override
    public StringTag1_16_5 asStringTag() {
        return null;
    }

    @Override
    public boolean contains(String key) {
        return this.wrapped.contains(key);
    }

    @Override
    public CompoundTag1_16_5 getCompoundTag(String key) {
        return new CompoundTag1_16_5(this.wrapped.getCompound(key));
    }

    @Override
    public ListTag1_16_5 getListTag(String key) {
        return (ListTag1_16_5)getTag(key).asListTag();
    }

    @Override
    public PrimitiveTag1_16_5 getPrimitiveTag(String key) {
        return (PrimitiveTag1_16_5)getTag(key).asPrimitiveTag();
    }

    @Override
    public String getString(String key) {
        return this.wrapped.getString(key);
    }

    @Override
    public BaseTagAPI<?> getTag(String key) {
        return TagHelper.getWrapped(this.wrapped.get(key));
    }
    
    @Override
    public boolean isCompound() {
        return true;
    }
    
    @Override
    public boolean isList() {
        return false;
    }
    
    @Override
    public boolean isPrimitive() {
        return false;
    }
    
    @Override
    public boolean isString() {
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
    
    @Override
    public void putInt(String key, int value) {
        this.wrapped.putInt(key,value);
    }
    
    @Override public void putLong(String key, long l) {
        this.wrapped.putLong(key,l);
    }
    
    @Override public void putShort(String key, short s) {
        this.wrapped.putShort(key,s);
    }
    
    @Override
    public void putString(String key, String value) {
        this.wrapped.putString(key,value);
    }

    @Override
    public void putTag(String key, BaseTagAPI<?> tag) {
        this.wrapped.put(key,(INBT)tag.getWrapped());
    }
}
