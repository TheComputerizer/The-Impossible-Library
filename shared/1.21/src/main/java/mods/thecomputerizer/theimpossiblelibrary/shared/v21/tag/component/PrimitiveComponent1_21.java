package mods.thecomputerizer.theimpossiblelibrary.shared.v21.tag.component;

import mods.thecomputerizer.theimpossiblelibrary.api.tag.PrimitiveTagAPI;
import net.minecraft.core.component.TypedDataComponent;

public class PrimitiveComponent1_21 extends PrimitiveTagAPI<TypedDataComponent<Number>> implements ComponentWrapper {

    public PrimitiveComponent1_21(Object component) {
        super(component);
    }

    @Override public boolean asBoolean() {
        return asByte()==(byte)1;
    }

    @Override public byte asByte() {
        return asNumber().byteValue();
    }

    @Override public double asDouble() {
        return asNumber().doubleValue();
    }

    @Override public float asFloat() {
        return asNumber().floatValue();
    }

    @Override public int asInt() {
        return asNumber().intValue();
    }

    @Override public long asLong() {
        return asNumber().longValue();
    }
    
    private Number asNumber() {
        return getIfNotNullOrDefault(TypedDataComponent::value,0);
    }

    @Override public short asShort() {
        return asNumber().shortValue();
    }
}