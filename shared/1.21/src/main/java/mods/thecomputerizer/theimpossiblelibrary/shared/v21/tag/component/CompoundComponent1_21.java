package mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.component;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.TagWrapper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.component.CustomData;

import java.util.Objects;

import static net.minecraft.world.item.component.CustomData.EMPTY;

public class CompoundComponent1_21 extends CompoundTagAPI<CustomData> implements ComponentWrapper {

    public CompoundComponent1_21(Object data) {
        super(data);
    }

    @Override public boolean contains(String key) {
        return getIfNotNullOrDefault(w -> w.contains(key),false);
    }
    
    /**
     * The tag needs to be modifiable without being required to get modified
     */
    @SuppressWarnings("deprecation")
    @Override public CompoundComponent1_21 getCompoundTag(String key) {
        CompoundTag tag = getIfNotNull(CustomData::getUnsafe);
        CustomData data = Objects.nonNull(tag) && tag.contains(key,10) ?
                CustomData.of(tag.getCompound(key)) : EMPTY;
        return new CompoundComponent1_21(data);
    }

    @Override public String getString(String key) {
        return getIfNotNull(w -> w.copyTag().getString(key));
    }
    
    /**
     * The tag needs to be modifiable without being required to get modified
     */
    @SuppressWarnings("deprecation")
    @Override public BaseTagAPI<?> getTag(String key) {
        return getIfNotNull(w -> TagHelper.getWrapped(w.getUnsafe().get(key)));
    }
    
    @Override public boolean isEmpty() {
        return getIfNotNullOrDefault(CustomData::isEmpty,true);
    }

    @Override public void putTag(String key, BaseTagAPI<?> api) {
        if(Objects.nonNull(this.wrapped) && api instanceof TagWrapper)
            this.wrapped.update(tag -> tag.put(key,api.unwrap()));
        else TILRef.logError("Cannot add data component to CompoundTag");
    }
    
    @Override public String toPrettyString() { //TODO Is there a way to pretty print components?
        return getIfNotNull(Object::toString);
    }
}