package mods.thecomputerizer.theimpossiblelibrary.api.util;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class GenericUtils {



    /**
     * This attempts to cast a value stored as object to a generic type.
     * If the object is a primitive type a stronger cast is performed by getting the string value and reparsing it
     */
    @SuppressWarnings("unchecked")
    public static <T> T castGenericType(@Nullable Object obj, Class<T> valType) {
        if(Objects.isNull(obj)) return null;
        return isPrimitiveInstance(obj) ? (T)parseGenericType(obj.toString(),valType) : valType.cast(obj);
    }

    /**
     * Checks if the input class is assignable from any of the input class types
     */
    public static boolean isAnyType(Class<?> clazz, Class<?> ... classTypes) {
        for(Class<?> type : classTypes)
            if(type.isAssignableFrom(clazz)) return true;
        return false;
    }

    /**
     * Checks if the input value extends any of the input class types
     */
    public static boolean isInstanceAnyType(Object obj, Class<?> ... types) {
        return isAnyType(obj.getClass(),types);
    }

    /**
     * The list type can't be checked if it's empty, so it has to return false if that is the case.
     */
    public static boolean isListAnyType(List<?> list, Class<?> ... classTypes) {
        return !list.isEmpty() && isInstanceAnyType(list.get(0), classTypes);
    }

    /**
     * Checks if the class is a primitive type.
     * All Number extensions and Strings are treated as primitive for parsing purposes.
     */
    public static boolean isPrimitive(Class<?> clazz) {
        return isAnyType(clazz,boolean.class,Number.class,String.class);
    }

    /**
     * Checks if the object is a primitive type.
     * All Number extensions and Strings are treated as primitive for parsing purposes.
     */
    public static boolean isPrimitiveInstance(Object obj) {
        return isPrimitive(obj.getClass());
    }

    /**
     * This only handles primitive types for the most part, so it may be better to just do whatever you want manually
     */
    public static Object parseGenericType(String unparsed, Class<?> valType) {
        switch(valType.getSimpleName()) {
            case "Boolean": return Boolean.parseBoolean(unparsed);
            case "Byte": return Byte.parseByte(unparsed);
            case "Date": return Date.parse(unparsed);
            case "Double": return Double.parseDouble(unparsed);
            case "Float": return Float.parseFloat(unparsed);
            case "Integer": return Integer.parseInt(unparsed);
            case "Long": return Long.parseLong(unparsed);
            case "Short": return Short.parseShort(unparsed);
            default: return unparsed;
        }
    }

    /**
     * Assumes the value is stored as a string
     */
    public static Object parseGenericFromTag(CompoundTag tag) {
        String className = tag.getString("type");
        if(className.isEmpty()) return null;
        try {
            Class<?> valType = Class.forName(className);
            if(List.class.isAssignableFrom(valType)) return readFromList(tag.get("value"));
            String storedVal = tag.getString("value");
            if(storedVal.isEmpty()) return null;
            return parseGenericType(storedVal,valType);
        } catch (ClassNotFoundException ex) {
            Constants.LOGGER.error("Could not find class name {} when parsing a generic object from NBT!",className,ex);
        }
        return null;
    }

    private static List<?> readFromList(Tag base) {
        if(!(base instanceof ListTag list)) return null;
        List<Object> ret = new ArrayList<>();
        for(Tag test : list) {
            if(!(test instanceof CompoundTag tag)) return null;
            ret.add(parseGenericFromTag(tag));
        }
        return ret;
    }

    public static void writeGenericToTag(CompoundTag tag, Object obj) {
        tag.putString("type",obj.getClass().getName());
        if(obj instanceof List<?>) tag.put("value",writeList(obj));
        else tag.putString("value",obj.toString());
    }

    private static ListTag writeList(Object obj) {
        ListTag list = new ListTag();
        List<?> val = (List<?>)obj;
        for(Object element : val) {
            CompoundTag tag = new CompoundTag();
            tag.putString("type",element.getClass().getName());
            if(element instanceof List<?>) tag.put("value",writeList(element));
            else tag.putString("value",element.toString());
        }
        return list;
    }
}
