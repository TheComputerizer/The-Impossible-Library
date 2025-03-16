package mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.component;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.ListTag1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.PrimitiveTag1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.StringTag1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.TagWrapper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.component.CustomData;

import static net.minecraft.world.item.component.CustomData.EMPTY;

public class CompoundComponent1_21 extends CompoundTagAPI<CustomData> implements ComponentWrapper {

    public CompoundComponent1_21(CustomData data) {
        super(data);
    }
    
    @Override public CompoundComponent1_21 asCompoundTag() {
        return this;
    }
    
    @Override public ListTag1_21 asListTag() {
        return null;
    }
    
    @Override public PrimitiveTag1_21 asPrimitiveTag() {
        return null;
    }
    
    @Override public StringTag1_21 asStringTag() {
        return null;
    }

    @Override public boolean contains(String key) {
        return this.wrapped.contains(key);
    }
    
    
    /**
     * The tag needs to be modifiable without being required to get modified
     */
    @SuppressWarnings("deprecation")
    @Override public CompoundComponent1_21 getCompoundTag(String key) {
        CompoundTag tag = this.wrapped.getUnsafe();
        CustomData data = tag.contains(key,10) ? CustomData.of(tag.getCompound(key)) : EMPTY;
        return new CompoundComponent1_21(data);
    }

    @Override public ListTag1_21 getListTag(String key) {
        return (ListTag1_21)getTag(key).asListTag();
    }

    @Override public PrimitiveTag1_21 getPrimitiveTag(String key) {
        return (PrimitiveTag1_21)getTag(key).asPrimitiveTag();
    }

    @Override public String getString(String key) {
        return this.wrapped.copyTag().getString(key);
    }
    
    /**
     * The tag needs to be modifiable without being required to get modified
     */
    @SuppressWarnings("deprecation")
    @Override public BaseTagAPI<?> getTag(String key) {
        return TagHelper.getWrapped(this.wrapped.getUnsafe().get(key));
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
        this.wrapped.update(tag -> tag.putBoolean(key,b));
    }
    
    @Override public void putByte(String key, byte b) {
        this.wrapped.update(tag -> tag.putByte(key,b));
    }
    
    @Override public void putDouble(String key, double d) {
        this.wrapped.update(tag -> tag.putDouble(key,d));
    }
    
    @Override public void putFloat(String key, float f) {
        this.wrapped.update(tag -> tag.putFloat(key,f));
    }
    
    @Override public void putInt(String key, int value) {
        this.wrapped.update(tag -> tag.putInt(key,value));
    }
    
    @Override public void putLong(String key, long l) {
        this.wrapped.update(tag -> tag.putLong(key,l));
    }
    
    @Override public void putShort(String key, short s) {
        this.wrapped.update(tag -> tag.putShort(key,s));
    }
    
    @Override public void putString(String key, String value) {
        this.wrapped.update(tag -> tag.putString(key,value));
    }

    @Override public void putTag(String key, BaseTagAPI<?> api) {
        if(api instanceof TagWrapper) this.wrapped.update(tag -> tag.put(key,api.unwrap()));
        else TILRef.logError("Cannot add data component to CompoundTag");
    }
}