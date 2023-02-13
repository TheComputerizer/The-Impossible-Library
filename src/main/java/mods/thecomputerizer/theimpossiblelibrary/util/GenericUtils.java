package mods.thecomputerizer.theimpossiblelibrary.util;

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
}
