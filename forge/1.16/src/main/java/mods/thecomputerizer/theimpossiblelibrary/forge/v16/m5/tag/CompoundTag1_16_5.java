package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.nbt.StringNBT;

public class CompoundTag1_16_5 extends BaseTag1_16_5<CompoundNBT> implements CompoundTagAPI<CompoundNBT> {

    public CompoundTag1_16_5(CompoundNBT tag) {
        super(tag);
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
        return getTag(key).asListTag();
    }

    @Override
    public PrimitiveTag1_16_5 getPrimitiveTag(String key) {
        return getTag(key).asPrimitiveTag();
    }

    @Override
    public String getString(String key) {
        return this.wrapped.getString(key);
    }

    @Override
    public BaseTag1_16_5<?> getTag(String key) {
        return new BaseTag1_16_5<>(this.wrapped.get(key));
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
    
    @Override public void putTag(String key, CompoundTagAPI<?> tag) {
        this.wrapped.put(key,(CompoundNBT)tag.getWrapped());
    }
    
    @Override public void putTag(String key, ListTagAPI<?> tag) {
        this.wrapped.put(key,(ListNBT)tag.getWrapped());
    }
    
    @Override public void putTag(String key, PrimitiveTagAPI<?> tag) {
        this.wrapped.put(key,(NumberNBT)tag.getWrapped());
    }
    
    @Override public void putTag(String key, StringTagAPI<?> tag) {
        this.wrapped.put(key,(StringNBT)tag.getWrapped());
    }
}
