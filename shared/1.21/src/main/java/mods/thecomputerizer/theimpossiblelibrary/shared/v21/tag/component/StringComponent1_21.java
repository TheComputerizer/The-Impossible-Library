package mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.component;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.CompoundTag1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.ListTag1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.PrimitiveTag1_21;
import net.minecraft.core.component.TypedDataComponent;

public class StringComponent1_21 extends StringTagAPI<TypedDataComponent<String>> {

    public StringComponent1_21(TypedDataComponent<String> component) {
        super(component);
    }
    
    @Override public CompoundTag1_21 asCompoundTag() {
        return null;
    }
    
    @Override public ListTag1_21 asListTag() {
        return null;
    }
    
    @Override public PrimitiveTag1_21 asPrimitiveTag() {
        return null;
    }
    
    @Override public StringComponent1_21 asStringTag() {
        return this;
    }
    
    @Override public boolean isCompound() {
        return false;
    }
    
    @Override public boolean isList() {
        return false;
    }
    
    @Override public boolean isPrimitive() {
        return false;
    }
    
    @Override public boolean isString() {
        return true;
    }

    @Override public String getValue() {
        return this.wrapped.value();
    }
}