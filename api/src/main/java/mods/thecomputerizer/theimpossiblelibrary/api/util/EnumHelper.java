package mods.thecomputerizer.theimpossiblelibrary.api.util;

import org.apache.commons.lang3.EnumUtils;

import java.util.Objects;

public class EnumHelper {

    public static <E extends Enum<E>> E getEnumOrDefault(String name, Class<E> clazz, E defVal) {
        E e = EnumUtils.getEnum(clazz,name);
        return Objects.nonNull(e) ? e : defVal;
    }
}