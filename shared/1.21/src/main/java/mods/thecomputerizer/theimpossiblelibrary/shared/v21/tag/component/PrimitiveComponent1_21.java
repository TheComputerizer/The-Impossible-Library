package mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.component;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.CompoundTag1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.ListTag1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.StringTag1_21;
import net.minecraft.core.component.TypedDataComponent;

public class PrimitiveComponent1_21 extends PrimitiveTagAPI<TypedDataComponent<Number>> {

    public PrimitiveComponent1_21(TypedDataComponent<Number> component) {
        super(component);
    }

    @Override public boolean asBoolean() {
        return asByte()==(byte)1;
    }

    @Override public byte asByte() {
        return this.wrapped.value().byteValue();
    }
    
    @Override public CompoundTag1_21 asCompoundTag() {
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
    
    @Override public ListTag1_21 asListTag() {
        return null;
    }

    @Override public long asLong() {
        return this.wrapped.value().longValue();
    }
    
    @Override public PrimitiveComponent1_21 asPrimitiveTag() {
        return this;
    }

    @Override public short asShort() {
        return this.wrapped.value().shortValue();
    }
    
    @Override public StringTag1_21 asStringTag() {
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