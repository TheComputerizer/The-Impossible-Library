package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.tag;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import net.minecraft.nbt.CompoundTag;

import java.util.Objects;

public class CompoundTag1_16_5 extends CompoundTagAPI<CompoundTag> {

    public CompoundTag1_16_5(Object tag) {
        super(tag);
    }

    @Override public boolean contains(String key) {
        return getIfNotNullOrDefault(w -> w.contains(key),false);
    }

    @Override public BaseTagAPI<?> getTag(String key) {
        return getIfNotNull(w -> TagHelper.getWrapped(w.get(key)));
    }
    
    @Override public boolean isEmpty() {
        return getIfNotNullOrDefault(CompoundTag::isEmpty,true);
    }

    @Override public void putTag(String key, BaseTagAPI<?> tag) {
        if(Objects.nonNull(this.wrapped)) this.wrapped.put(key,tag.unwrap());
    }
    
    @Override public String toPrettyString() {
        return getIfNotNull(w -> w.getPrettyDisplay().getString());
    }
}