package mods.thecomputerizer.theimpossiblelibrary.api.parameter;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.iterator.IterableHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Getter
public abstract class Parameter<T> {

    protected final T defaultValue;
    protected T value;

    protected Parameter(T defaultValue) {
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    protected Parameter(ByteBuf buf) {
        this.defaultValue = read(buf);
        this.value = read(buf);
    }
    
    public abstract Parameter<T> copy();
    
    @Override public boolean equals(Object other) {
        return other instanceof Parameter<?> && GenericUtils.matches(this.value,((Parameter<?>)other).value);
    }
    
    public boolean getAsBoolean(String name) {
        return Boolean.parseBoolean(getAsString());
    }
    
    @IndirectCallers
    public byte getAsByte() {
        return getAsByte("parameter");
    }
    
    public byte getAsByte(String name) {
        return getAsNumber(Number::byteValue,s -> RandomHelper.randomByte(name+"_as_number",s,(byte)0));
    }
    
    public ColorCache getAsColor() {
        return ColorHelper.getColor(getAsString());
    }
    
    @IndirectCallers
    public double getAsDouble() {
        return getAsDouble("parameter");
    }
    
    public double getAsDouble(String name) {
        return getAsNumber(Number::doubleValue,s -> RandomHelper.randomDouble(name+"_as_number",s,0d));
    }
    
    @IndirectCallers
    public float getAsFloat() {
        return getAsFloat("parameter");
    }
    
    public float getAsFloat(String name) {
        return getAsNumber(Number::floatValue,s -> RandomHelper.randomFloat(name+"_as_number",s,0f));
    }
    
    public int getAsInt() {
        return getAsInt("parameter");
    }
    
    public int getAsInt(String name) {
        return getAsNumber(Number::intValue,s -> RandomHelper.randomInt(name+"_as_number",s,0));
    }
    
    @IndirectCallers
    public long getAsLong() {
        return getAsLong("parameter");
    }
    
    public long getAsLong(String name) {
        return getAsNumber(Number::longValue,s -> RandomHelper.randomLong(name+"_as_number",s,0L));
    }
    
    @IndirectCallers
    public short getAsShort() {
        return getAsShort("parameter");
    }
    
    public short getAsShort(String name) {
        return getAsNumber(Number::shortValue,s -> RandomHelper.randomShort(name+"_as_number",s,(short)0));
    }
    
    public List<?> getAsList() {
        return Collections.singletonList(this.value);
    }
    
    @IndirectCallers
    public Number getAsNumber() {
        return getAsNumber("parameter");
    }
    
    public Number getAsNumber(String name) {
        return getAsNumber(n -> n,s -> RandomHelper.randomDouble(name+"_as_number",s,0d));
    }
    
    protected <N extends Number> N getAsNumber(Function<Number,N> fromNumber, Function<String,N> fromString) {
        return getAsNumber(this.value,fromNumber,fromString);
    }
    
    protected <N extends Number> N getAsNumber(@Nullable Object value, Function<Number,N> fromNumber,
            Function<String,N> fromString) {
        if(Objects.isNull(value)) return fromNumber.apply(0);
        if(value instanceof Number) return fromNumber.apply((Number)value);
        if(value instanceof String) return fromString.apply((String)value);
        if(value instanceof Boolean) return fromNumber.apply((Boolean)value ? 1 : 0);
        if(value instanceof Iterable<?>)
            return getAsNumber(IterableHelper.getElement(0,(Collection<?>)value),fromNumber,fromString);
        if(value instanceof Object[]) {
            Object[] array = (Object[])value;
            return getAsNumber(ArrayHelper.isNotEmpty(array) ? array[0] : 0,fromNumber,fromString);
        }
        return fromString.apply(String.valueOf(value));
    }
    
    public String getAsString() {
        return String.valueOf(this.value);
    }
    
    public abstract boolean isBool();
    public abstract boolean isByte();

    public boolean isDefault() {
        return GenericUtils.matches(this.value,this.defaultValue);
    }
    
    public abstract boolean isDouble();
    public abstract boolean isFloat();
    public abstract boolean isInt();
    public abstract boolean isList();
    public abstract boolean isLong();
    public abstract boolean isNumber();
    public abstract boolean isPrimitive();
    public abstract boolean isShort();
    public abstract boolean isString();

    protected abstract T read(ByteBuf buf);
    public abstract void setValue(@Nullable Object value);
    
    @Override public String toString() {
        return String.valueOf(this.value);
    }

    public void write(ByteBuf buf) {
        NetworkHelper.writeString(buf,getClass().getName());
        write(buf,this.defaultValue);
        write(buf,this.value);
    }

    protected abstract void write(ByteBuf buf, T val);
}