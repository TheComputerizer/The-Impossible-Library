package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

public class CompoundTag1_12_2 extends BaseTag1_12_2<NBTTagCompound> implements CompoundTagAPI<NBTTagCompound> {

    public CompoundTag1_12_2(NBTTagCompound tag) {
        super(tag);
    }

    @Override
    public boolean contains(String key) {
        return this.wrapped.hasKey(key);
    }

    @Override
    public CompoundTag1_12_2 getCompoundTag(String key) {
        return new CompoundTag1_12_2(this.wrapped.getCompoundTag(key));
    }

    @Override
    public ListTag1_12_2 getListTag(String key) {
        return (ListTag1_12_2)getTag(key).asListTag();
    }

    @Override
    public PrimitiveTag1_12_2 getPrimitiveTag(String key) {
        return (PrimitiveTag1_12_2)getTag(key).asPrimitiveTag();
    }

    @Override
    public String getString(String key) {
        return this.wrapped.getString(key);
    }

    @Override
    public BaseTagAPI<?> getTag(String key) {
        return new BaseTag1_12_2<>(this.wrapped.getTag(key));
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
    
    @Override
    public void putInt(String key, int value) {
        this.wrapped.setInteger(key,value);
    }
    
    @Override public void putLong(String key, long l) {
        this.wrapped.setLong(key,l);
    }
    
    @Override public void putShort(String key, short s) {
        this.wrapped.setShort(key,s);
    }
    
    @Override
    public void putString(String key, String value) {
        this.wrapped.setString(key,value);
    }

    @Override
    public void putTag(String key, BaseTagAPI<?> tag) {
        this.wrapped.setTag(key,(NBTBase)tag.getWrapped());
    }
    
    @Override public void putTag(String key, CompoundTagAPI<?> tag) {
        this.wrapped.setTag(key,(NBTTagCompound)tag.getWrapped());
    }
    
    @Override public void putTag(String key, ListTagAPI<?> tag) {
        this.wrapped.setTag(key,(NBTTagList)tag.getWrapped());
    }
    
    @Override public void putTag(String key, PrimitiveTagAPI<?> tag) {
        this.wrapped.setTag(key,(NBTPrimitive)tag.getWrapped());
    }
    
    @Override public void putTag(String key, StringTagAPI<?> tag) {
        this.wrapped.setTag(key,(NBTTagString)tag.getWrapped());
    }
}
