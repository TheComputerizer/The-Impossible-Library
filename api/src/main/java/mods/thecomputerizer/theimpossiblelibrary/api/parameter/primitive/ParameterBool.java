package mods.thecomputerizer.theimpossiblelibrary.api.parameter.primitive;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.parameter.Parameter;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Function;

public class ParameterBool extends Parameter<Boolean> {

    public ParameterBool(boolean defaultValue) {
        super(defaultValue);
    }

    @SuppressWarnings("unused") //See ParameterHelper#parse
    public ParameterBool(ByteBuf buf) {
        super(buf);
    }
    
    @Override public ParameterBool copy() {
        ParameterBool copy = new ParameterBool(this.defaultValue);
        copy.value = this.value;
        return copy;
    }
    
    @Override public boolean getAsBoolean(String name) {
        return this.value;
    }
    
    @Override protected <V extends Number> V getAsNumber(@Nullable Object value, Function<Number,V> fromNumber,
            Function<String,V> fromString) {
        return fromNumber.apply(this.value ? 1 : 0);
    }
    
    @Override public boolean isBool() {
        return true;
    }
    
    @Override public boolean isByte() {
        return false;
    }
    
    @Override public boolean isDouble() {
        return false;
    }
    
    @Override public boolean isFloat() {
        return false;
    }
    
    @Override public boolean isInt() {
        return false;
    }
    
    @Override public boolean isList() {
        return false;
    }
    
    @Override public boolean isLong() {
        return false;
    }
    
    @Override public boolean isNumber() {
        return false;
    }
    
    @Override public boolean isPrimitive() {
        return true;
    }
    
    @Override public boolean isShort() {
        return false;
    }
    
    @Override public boolean isString() {
        return false;
    }
    
    @Override protected Boolean read(ByteBuf buf) {
        return buf.readBoolean();
    }
    
    @Override public void setValue(@Nullable Object value) {
        if(Objects.isNull(value)) this.value = false;
        else if(value instanceof Boolean) this.value = (Boolean)value;
        else if(value instanceof Number) this.value = ((Number)value).intValue()==1;
        else this.value = Boolean.parseBoolean(value.toString());
    }
    
    @Override protected void write(ByteBuf buf, Boolean val) {
        buf.writeBoolean(val);
    }
}
