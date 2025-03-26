package mods.thecomputerizer.theimpossiblelibrary.api.util;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

public class EnumHelper {
    
    @IndirectCallers
    public static <E extends Enum<E>> E getEnumOrDefault(String name, Class<E> clazz, E defVal) {
        try {
            return Enum.valueOf(clazz,name);
        } catch(Throwable t) {
            TILRef.logError("Failed to get enum {} in {}",name,clazz);
        }
        return defVal;
    }
}