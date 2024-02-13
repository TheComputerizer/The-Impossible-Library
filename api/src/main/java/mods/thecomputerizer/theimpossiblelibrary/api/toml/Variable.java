package mods.thecomputerizer.theimpossiblelibrary.api.toml;

import io.netty.buffer.ByteBuf;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.network.NetworkHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;

import javax.annotation.Nullable;
import java.util.*;

/**
 * A basic variable type line handling anything the TOML format accepts.
 * Accepted types are string, boolean, long, double, Date, and list, where list can have a subset of any accepted type
 * Assumes the entire variable and its values are contained within a single line
 */
public class Variable extends AbstractType {

    /**
     * The name the variable is assigned to
     */
    private final String name;

    /**
     * The value of the variable
     */
    private Object value;

    private boolean isValid = true;

    /**
     * For decoding from NBT. Cannot handle null values
     */
    public Variable(CompoundTag tag, @Nullable Table parentTable) {
        super(tag, parentTable);
        this.name = tag.getString("name");
        this.value = this.name.isEmpty() ? null : GenericUtils.parseGenericFromTag(tag);
        this.isValid = Objects.nonNull(this.value);
    }

    /**
     * For decoding from a packet. Cannot handle null values
     */
    public Variable(ByteBuf buf, @Nullable Table parentTable) {
        super(buf, parentTable);
        this.name = NetworkHelper.readString(buf);
        this.value = NetworkHelper.parseGenericObj(buf);
        this.isValid = Objects.nonNull(this.value);
    }

    public Variable(int absoluteIndex, @Nullable Table parentTable, String name, Object value) {
        super(absoluteIndex, parentTable);
        this.name = name;
        this.value = value;
    }

    /**
     * Used to determine if the variable was properly decoded from a packet
     */
    public boolean isValid() {
        return this.isValid;
    }

    public String getValueType() {
        return this.value.getClass().getName();
    }

    public String getName() {
        return this.name;
    }

    /**
     * Checks to see if the input name matches
     */
    public boolean is(String name) {
        return !Objects.isNull(name) && !Objects.isNull(this.name) && name.matches(this.name);
    }

    public Object get() {
        return this.value;
    }

    public void set(Object val) {
        this.value = val;
    }

    public void invert() {
        getAsBool(true).ifPresent(aBoolean -> set(!aBoolean));
    }

    /**
     * Checks to see if the value is a boolean or a string that is a valid boolean if parsing is enabled, otherwise
     * returns an empty Optional.
     */
    public Optional<Boolean> getAsBool(boolean allowParse) {
        if (this.value instanceof Boolean) return Optional.of((boolean) this.value);
        if (!(this.value instanceof String)) return Optional.empty();
        if (allowParse) {
            if (((String) this.value).trim().toLowerCase().matches("true")) return Optional.of(true);
            if (((String) this.value).trim().toLowerCase().matches("false")) return Optional.of(false);
        }
        return Optional.empty();
    }

    /**
     * Checks to see if the value is a string, otherwise returns an empty optional unless allowToString is enabled
     */
    public Optional<String> getAsString(boolean allowToString) {
        return this.value instanceof String ? Optional.of((String)this.value) :
                allowToString ? Optional.of(this.value.toString()) : Optional.empty();
    }

    /**
     * Checks to see if the value is any accepted number type or a string that can be parsed into a number if parsing is
     * enabled, otherwise returns an empty Optional.
     */
    public Optional<Long> getAsLong(boolean allowParse) {
        if(this.value instanceof Long || this.value instanceof Integer ||
                this.value instanceof Float || this.value instanceof Double)
            return Optional.of(((Number)this.value).longValue());
        if(this.value instanceof String && allowParse) {
            try {
                return Optional.of(Long.parseLong((String)this.value));
            } catch (NumberFormatException ignored) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    /**
     * Checks to see if the value is an integer or long, otherwise returns an empty Optional.
     */
    public Optional<Long> getAsLongStrict() {
        return this.value instanceof Long || this.value instanceof Integer ?
                Optional.of(((Number)this.value).longValue()) : Optional.empty();
    }

    /**
     * Checks to see if the value is any accepted number type or a string that can be parsed into a number if parsing is
     * enabled, otherwise returns an empty Optional.
     */
    public Optional<Integer> getAsInt(boolean allowParse) {
        if(this.value instanceof Long || this.value instanceof Integer ||
                this.value instanceof Float || this.value instanceof Double)
            return Optional.of(((Number)this.value).intValue());
        if(this.value instanceof String && allowParse) {
            try {
                return Optional.of(Integer.parseInt((String)this.value));
            } catch (NumberFormatException ignored) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    /**
     * Checks to see if the value is an integer or long, otherwise returns an empty Optional.
     */
    public Optional<Integer> getAsIntStrict() {
        return this.value instanceof Long || this.value instanceof Integer ?
                Optional.of(((Number)this.value).intValue()) : Optional.empty();
    }

    /**
     * Checks to see if the value is any accepted number type or a string that can be parsed into a number if parsing is
     * enabled, otherwise returns an empty Optional.
     */
    public Optional<Double> getAsDouble(boolean allowParse) {
        if(this.value instanceof Long || this.value instanceof Integer ||
                this.value instanceof Float || this.value instanceof Double)
            return Optional.of(((Number)this.value).doubleValue());
        if(this.value instanceof String && allowParse) {
            try {
                return Optional.of(Double.parseDouble((String)this.value));
            } catch (NumberFormatException ignored) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    /**
     * Checks to see if the value is a float or double, otherwise returns an empty Optional.
     */
    public Optional<Double> getAsDoubleStrict() {
        return this.value instanceof Double || this.value instanceof Float ?
                Optional.of(((Number)this.value).doubleValue()) : Optional.empty();
    }

    /**
     * Checks to see if the value is any accepted number type or a string that can be parsed into a number if parsing is
     * enabled, otherwise returns an empty Optional.
     */
    public Optional<Float> getAsFloat(boolean allowParse) {
        if(this.value instanceof Long || this.value instanceof Integer ||
                this.value instanceof Float || this.value instanceof Double)
            return Optional.of(((Number)this.value).floatValue());
        if(this.value instanceof String && allowParse) {
            try {
                return Optional.of(Float.parseFloat((String)this.value));
            } catch (NumberFormatException ignored) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    /**
     * Checks to see if the value is a float or double, otherwise returns an empty Optional.
     */
    public Optional<Float> getAsFloatStrict() {
        return this.value instanceof Double || this.value instanceof Float ?
                Optional.of(((Number)this.value).floatValue()) : Optional.empty();
    }

    /**
     * Checks to see if the value is a boolean or a string if parsing is enabled, otherwise returns an empty Optional.
     * Does not check to see if the string getting parsed into a Date is valid so use that with caution
     */
    public Optional<Date> getAsDate(boolean allowParse) {
        if(!(this.value instanceof Date)) {
            if(!(this.value instanceof String)) return Optional.empty();
            return allowParse ? Optional.of(new Date(Date.parse((String)this.value))) : Optional.empty();
        }
        else return Optional.of((Date)this.value);
    }

    /**
     * Checks to see if the value is a List and optionally if the list type matches any of the class inputs, otherwise
     * returns an empty Optional. If there are no class inputs, it will match anything
     */
    public Optional<List<?>> getAsList(Class<?> ... optionalClassMatching) {
        if(this.value instanceof List<?>) {
            if(optionalClassMatching.length==0) return Optional.of((List<?>)this.value);
            return GenericUtils.isListAnyType((List<?>)this.value,optionalClassMatching) ?
                    Optional.of((List<?>)this.value) : Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public List<String> toLines() {
        String line = getSpacing()+this.name+" = "+
                (!(this.value instanceof List<?>) ? !(this.value instanceof String) ?
                        this.value : "\""+this.value+"\"" : TextHelper.compileCollection((List<?>)this.value));
        return Collections.singletonList(line);
    }

    @Override
    public void write(ByteBuf buf) {
        super.write(buf);
        NetworkHelper.writeString(buf,this.name);
        NetworkHelper.writeGenericObj(buf,this.value);
    }

    public CompoundTag writeToTag() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("absoluteIndex",this.getAbsoluteIndex());
        tag.putString("name",this.name);
        GenericUtils.writeGenericToTag(tag,this.value);
        return tag;
    }
}
