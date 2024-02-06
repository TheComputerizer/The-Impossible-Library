package mods.thecomputerizer.theimpossiblelibrary.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenericUtils {

    public static boolean isAnyType(Object val, Class<?> ... classTypes) {
        for(Class<?> type : classTypes)
            if(type.isAssignableFrom(val.getClass())) return true;
        return false;
    }

    /**
     * The list type can't be checked if it's empty, so it has to return false if that is the case.
     */
    public static boolean isListAnyType(List<?> list, Class<?> ... classTypes) {
        return !list.isEmpty() && isAnyType(list.get(0), classTypes);
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
