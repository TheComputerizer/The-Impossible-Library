package mods.thecomputerizer.theimpossiblelibrary.api.parameter;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.io.LoggableAPI;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.WHITE;

public interface ParameterMap extends LoggableAPI {
    
    Parameter<?> getParameter(String name);
    
    default <T> T getParameterAs(String name, BiFunction<Parameter<?>,String,T> getter) {
        return getParameterAs(name,null,getter);
    }
    
    default <T> T getParameterAs(String name, T defVal, BiFunction<Parameter<?>,String,T> getter) {
        Parameter<?> parameter = getParameter(name);
        return Objects.nonNull(parameter) ? getter.apply(parameter,name) : defVal;
    }
    
    @IndirectCallers
    default boolean getParameterAsBoolean(String name) {
        return getParameterAsBoolean(name,false);
    }
    
    default boolean getParameterAsBoolean(String name, boolean defVal) {
        return getParameterAs(name,defVal,Parameter::getAsBoolean);
    }
    
    @IndirectCallers
    default byte getParameterAsByte(String name) {
        return getParameterAsByte(name,(byte)0);
    }
    
    default byte getParameterAsByte(String name, byte defVal) {
        return getParameterAs(name,defVal,Parameter::getAsByte);
    }
    
    @IndirectCallers
    default ColorCache getParameterAsColor(String name) {
        return getParameterAsColor(name,WHITE);
    }
    
    default ColorCache getParameterAsColor(String name, ColorCache defVal) {
        return getParameterAs(name,defVal,(p,ignored) -> p.getAsColor());
    }
    
    @IndirectCallers
    default double getParameterAsDouble(String name) {
        return getParameterAsDouble(name,0d);
    }
    
    default double getParameterAsDouble(String name, double defVal) {
        return getParameterAs(name,defVal,Parameter::getAsDouble);
    }
    
    @IndirectCallers
    default float getParameterAsFloat(String name) {
        return getParameterAsFloat(name,0f);
    }
    
    default float getParameterAsFloat(String name, float defVal) {
        return getParameterAs(name,defVal,Parameter::getAsFloat);
    }
    
    @IndirectCallers
    default int getParameterAsInt(String name) {
        return getParameterAsInt(name,0);
    }
    
    default int getParameterAsInt(String name, int defVal) {
        return getParameterAs(name,defVal,Parameter::getAsInt);
    }
    
    @IndirectCallers
    default List<?> getParameterAsList(String name) {
        return getParameterAsList(name,Collections.emptyList());
    }
    
    default List<?> getParameterAsList(String name, List<?> defVal) {
        return getParameterAs(name,defVal,(p,ignored) -> p.getAsList());
    }
    
    @IndirectCallers
    default long getParameterAsLong(String name) {
        return getParameterAsLong(name,0L);
    }
    
    default long getParameterAsLong(String name, long defVal) {
        return getParameterAs(name,defVal,Parameter::getAsLong);
    }
    
    @IndirectCallers
    default Number getParameterAsNumber(String name) {
        return getParameterAsNumber(name,0);
    }
    
    default Number getParameterAsNumber(String name, Number defVal) {
        return getParameterAs(name,defVal,Parameter::getAsNumber);
    }
    
    default Object getParameterAsObject(String name) {
        return getParameterAsObject(name,null);
    }
    
    default Object getParameterAsObject(String name, Object defVal) {
        return getParameterAs(name,defVal,(p,ignored) -> p.getValue());
    }
    
    @IndirectCallers
    default short getParameterAsShort(String name) {
        return getParameterAsShort(name,(short)0);
    }
    
    default short getParameterAsShort(String name, short defVal) {
        return getParameterAs(name,defVal,Parameter::getAsShort);
    }
    
    @IndirectCallers
    default String getParameterAsString(String name) {
        return getParameterAsString(name,null);
    }
    
    default String getParameterAsString(String name, String defVal) {
        return getParameterAs(name,defVal,(p,ignored) -> p.getAsString());
    }
    
    @IndirectCallers
    default Map<String,Object> getValueMap() {
        return getValueMap(false);
    }
    
    default Map<String,Object> getValueMap(boolean filterNullValues) {
        Map<String,Object> map = new LinkedHashMap<>();
        for(String key : keys()) {
            Object value = getParameterAsObject(key);
            if(Objects.nonNull(value) || !filterNullValues) map.put(key,value);
        }
        return map;
    }
    
    Collection<String> keys();
    Collection<Parameter<?>> parameters();
}