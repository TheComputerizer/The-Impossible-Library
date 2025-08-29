package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Objects;

public class CompoundTag1_12_2 extends CompoundTagAPI<NBTTagCompound> {

    public CompoundTag1_12_2(Object tag) {
        super(tag);
    }
    
    @Override public boolean contains(String key) {
        return getIfNotNullOrDefault(w -> w.hasKey(key),false);
    }

    @Override public BaseTagAPI<?> getTag(String key) {
        return getIfNotNull(w -> TagHelper.getWrapped(w.getTag(key)));
    }
    
    @Override public boolean isEmpty() {
        return getIfNotNullOrDefault(NBTTagCompound::isEmpty,true);
    }

    @Override public void putTag(String key, BaseTagAPI<?> tag) {
        if(Objects.nonNull(this.wrapped)) this.wrapped.setTag(key,tag.unwrap());
    }
    
    @Override public String toPrettyString() { //TODO Is there a built in method for this in 1.12.2?
        return toString();
    }
}
