package mods.thecomputerizer.theimpossiblelibrary.util;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

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
    public static Object parseGenericType(String val, Class<?> valType) {
        Object ret;
        if(valType.isAssignableFrom(Number.class))
            ret = valType.isAssignableFrom(Long.class) ? Long.parseLong(val) :
                valType.isAssignableFrom(Integer.class) ? Integer.parseInt(val) :
                        valType.isAssignableFrom(Double.class) ? Double.parseDouble(val) :
                                valType.isAssignableFrom(Float.class) ? Float.parseFloat(val) :
                                        valType.isAssignableFrom(Short.class) ? Short.parseShort(val) :
                                                valType.isAssignableFrom(Byte.class) ? Byte.parseByte(val) : val;
        else ret = valType.isAssignableFrom(Boolean.class) ? Boolean.parseBoolean(val) :
                valType.isAssignableFrom(Date.class) ? Date.parse(val) : val;
        return ret;
    }

    /**
     * Assumes the value is stored as a string
     */
    public static Object parseGenericFromTag(NBTTagCompound tag) {
        String className = tag.getString("type");
        if(className.isEmpty()) return null;
        try {
            Class<?> valType = Class.forName(className);
            if(List.class.isAssignableFrom(valType)) return readFromList(tag.getTag("value"));
            String storedVal = tag.getString("value");
            if(storedVal.isEmpty()) return null;
            return parseGenericType(storedVal,valType);
        } catch (ClassNotFoundException ex) {
            Constants.LOGGER.error("Could not find class name {} when parsing a generic object from NBT!",className,ex);
        }
        return null;
    }

    private static List<?> readFromList(NBTBase base) {
        if(!(base instanceof NBTTagList)) return null;
        List<Object> ret = new ArrayList<>();
        NBTTagList list = (NBTTagList)base;
        for(NBTBase test : list) {
            if(!(test instanceof NBTTagCompound)) return null;
            NBTTagCompound tag = (NBTTagCompound)test;
            ret.add(parseGenericFromTag((NBTTagCompound)test));
        }
        return ret;
    }

    public static void writeGenericToTag(NBTTagCompound tag, Object obj) {
        tag.setString("type",obj.getClass().getName());
        if(obj instanceof List<?>) tag.setTag("value",writeList(obj));
        else tag.setString("value",obj.toString());
    }

    private static NBTTagList writeList(Object obj) {
        NBTTagList list = new NBTTagList();
        List<?> val = (List<?>)obj;
        for(Object element : val) {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("type",element.getClass().getName());
            if(element instanceof List<?>) tag.setTag("value",writeList(element));
            else tag.setString("value",element.toString());
        }
        return list;
    }
}
