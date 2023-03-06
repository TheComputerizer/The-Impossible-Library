package mods.thecomputerizer.theimpossiblelibrary.util;

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
     * By this only handles primitive types for the most part, so it may be better to just do whatever you want manually
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
}
