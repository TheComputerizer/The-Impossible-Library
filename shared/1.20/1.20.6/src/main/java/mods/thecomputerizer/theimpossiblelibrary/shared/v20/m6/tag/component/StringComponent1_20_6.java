package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag.component;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.StringTagAPI;
import net.minecraft.core.component.TypedDataComponent;

public class StringComponent1_20_6 extends StringTagAPI<TypedDataComponent<String>> implements ComponentWrapper {

    public StringComponent1_20_6(TypedDataComponent<String> component) {
        super(component);
    }
    
    @Override public CompoundComponent1_20_6 asCompoundTag() {
        return null;
    }
    
    @Override public ListComponent1_20_6 asListTag() {
        return null;
    }
    
    @Override public PrimitiveComponent1_20_6 asPrimitiveTag() {
        return null;
    }
    
    @Override public StringComponent1_20_6 asStringTag() {
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