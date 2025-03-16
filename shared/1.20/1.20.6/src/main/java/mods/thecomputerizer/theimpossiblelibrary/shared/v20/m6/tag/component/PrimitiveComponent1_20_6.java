package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m6.tag.component;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import net.minecraft.core.component.TypedDataComponent;

public class PrimitiveComponent1_20_6 extends PrimitiveTagAPI<TypedDataComponent<Number>> implements ComponentWrapper {

    public PrimitiveComponent1_20_6(TypedDataComponent<Number> component) {
        super(component);
    }

    @Override public boolean asBoolean() {
        return asByte()==(byte)1;
    }

    @Override public byte asByte() {
        return this.wrapped.value().byteValue();
    }
    
    @Override public CompoundComponent1_20_6 asCompoundTag() {
        return null;
    }

    @Override public double asDouble() {
        return this.wrapped.value().doubleValue();
    }

    @Override public float asFloat() {
        return this.wrapped.value().floatValue();
    }

    @Override public int asInt() {
        return this.wrapped.value().intValue();
    }
    
    @Override public ListComponent1_20_6 asListTag() {
        return null;
    }

    @Override public long asLong() {
        return this.wrapped.value().longValue();
    }
    
    @Override public PrimitiveComponent1_20_6 asPrimitiveTag() {
        return this;
    }

    @Override public short asShort() {
        return this.wrapped.value().shortValue();
    }
    
    @Override public StringComponent1_20_6 asStringTag() {
        return null;
    }
    
    @Override public boolean isCompound() {
        return false;
    }
    
    @Override public boolean isList() {
        return false;
    }
    
    @Override public boolean isPrimitive() {
        return true;
    }
    
    @Override public boolean isString() {
        return false;
    }
}