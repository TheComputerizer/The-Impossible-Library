package mods.thecomputerizer.theimpossiblelibrary.api.parameter.primitive;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.parameter.Parameter;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

@SuppressWarnings("unused")
public abstract class ParameterNumber<N extends Number> extends Parameter<N> {

    protected ParameterNumber(N defaultValue) {
        super(defaultValue);
    }

    protected ParameterNumber(ByteBuf buf) {
        super(buf);
    }

    public byte byteValue() {
        return this.value.byteValue();
    }
    
    @Override public abstract ParameterNumber<N> copy();

    public byte defaultByteValue() {
        return this.defaultValue.byteValue();
    }

    public double defaultDoubleValue() {
        return this.defaultValue.doubleValue();
    }

    public float defaultFloatValue() {
        return this.defaultValue.floatValue();
    }

    public int defaultIntValue() {
        return this.defaultValue.intValue();
    }

    public long defaultLongValue() {
        return this.defaultValue.longValue();
    }

    public short defaultShortValue() {
        return this.defaultValue.shortValue();
    }

    public double doubleValue() {
        return this.value.doubleValue();
    }

    public float floatValue() {
        return this.value.floatValue();
    }
    
    @Override public boolean getAsBoolean(String name) {
        return doubleValue()!=0d;
    }
    
    @Override public byte getAsByte() {
        return byteValue();
    }
    
    @Override public byte getAsByte(String name) {
        return byteValue();
    }
    
    @Override public double getAsDouble() {
        return doubleValue();
    }
    
    @Override public double getAsDouble(String name) {
        return doubleValue();
    }
    
    @Override public float getAsFloat() {
        return floatValue();
    }
    
    @Override public float getAsFloat(String name) {
        return floatValue();
    }
    
    @Override public int getAsInt() {
        return intValue();
    }
    
    @Override public int getAsInt(String name) {
        return intValue();
    }
    
    @Override public long getAsLong() {
        return longValue();
    }
    
    @Override public long getAsLong(String name) {
        return longValue();
    }
    
    @Override protected <V extends Number> V getAsNumber(@Nullable Object value, Function<Number,V> fromNumber,
            Function<String,V> fromString) {
        return fromNumber.apply(this.value);
    }
    
    @Override public short getAsShort() {
        return shortValue();
    }
    
    @Override public short getAsShort(String name) {
        return shortValue();
    }

    public int intValue() {
        return this.value.intValue();
    }
    
    @Override public boolean isBool() {
        return false;
    }
    
    @Override public boolean isList() {
        return false;
    }
    
    @Override public boolean isNumber() {
        return true;
    }
    
    @Override public boolean isPrimitive() {
        return true;
    }
    
    @Override public boolean isString() {
        return false;
    }

    public long longValue() {
        return this.value.longValue();
    }

    public short shortValue() {
        return this.value.shortValue();
    }
}