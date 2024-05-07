package mods.thecomputerizer.theimpossiblelibrary.api.util;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.iterator.IterableHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.BaseTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.ListTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.TagHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings("unused") public class GenericUtils {



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
     Runs non-strict equality rules.
     If both inputs are null, they are considered matching.
     If both elements are equal, they are considered matching.
     If the value is a string, and it equals the string value of the other, they are considered matching.
     If the value is a number, and it equals the numerical value of the other, they are considered matching.
     If the value is a boolean, it will match parsed booleans from strings or numbers (0=false,1=true)
     If the value is an array, iterable, or iterator further matching rules will be applied.
     If the type is unknown, the toString values of both inputs will be checked for equality.
     */
    public static boolean matches(@Nullable Object value, @Nullable Object other) {
        if(Objects.isNull(value)) return Objects.isNull(other);
        if(Objects.isNull(other)) return false;
        if(value.equals(other)) return true;
        if(value instanceof String) return value.equals(other.toString());
        if(value instanceof Number) return numberMatches((Number)value,other);
        if(value instanceof Boolean) {
            boolean bool = (Boolean)value;
            if(other instanceof Number) {
                int num = ((Number)other).intValue();
                return (bool && num==1) || (!bool && num==0); //Don't allow other numbers
            }
        } //The boolean check isn't guaranteed to return anything
        else if(value instanceof Object[]) return ArrayHelper.matches((Object[])value,other);
        else if(value instanceof Iterable<?>) return IterableHelper.matches((Iterable<?>)value,other);
        else if(value instanceof Iterator<?>) return IterableHelper.matches((Iterator<?>)value,other);
        return value.toString().equals(other.toString());
    }
    
    public static boolean numberMatches(Number number, Object other) {
        if(number instanceof Byte)
            return numberMatches((Byte)number,other,obj -> Byte.parseByte((String)other),(n1,n2) -> n1==n2.byteValue());
        if(number instanceof Double)
            return numberMatches((Double)number,other,obj -> Double.parseDouble((String)other),(n1,n2) -> n1==n2.doubleValue());
        if(number instanceof Float)
            return numberMatches((Float)number,other,obj -> Float.parseFloat((String)other),(n1,n2) -> n1==n2.floatValue());
        if(number instanceof Integer)
            return numberMatches((Integer)number,other,obj -> Integer.parseInt((String)other),(n1,n2) -> n1==n2.intValue());
        if(number instanceof Long)
            return numberMatches((Long)number,other,obj -> Long.parseLong((String)other),(n1,n2) -> n1==n2.longValue());
        if(number instanceof Short)
            return numberMatches((Short)number,other,obj -> Short.parseShort((String)other),(n1,n2) -> n1==n2.shortValue());
        return number.toString().equals(other.toString());
    }
    
    private static <N extends Number> boolean numberMatches(
            N number, Object other, Function<Object,N> fromString, BiFunction<N,Number,Boolean> numberEquals) {
        if(other instanceof String) return number.equals(fromString.apply(other));
        if(other instanceof Number) return numberEquals.apply(number,(Number)other);
        if(other instanceof Object[]) return ArrayHelper.matches((Object[])other,number);
        if(other instanceof Iterable<?>) return IterableHelper.matches((Iterable<?>)other,number);
        if(other instanceof Iterator<?>) return IterableHelper.matches((Iterator<?>)other,number);
        return number.toString().equals(other.toString());
    }

    /**
     * Assumes the value is stored as a string
     */
    public static Object parseGenericFromTag(CompoundTagAPI tag) {
        String className = tag.getString("type");
        if(className.isEmpty()) return null;
        try {
            Class<?> valType = Class.forName(className);
            if(List.class.isAssignableFrom(valType)) return readFromList(tag.getListTag("value"));
            String storedVal = tag.getString("value");
            if(storedVal.isEmpty()) return null;
            return parseGenericType(storedVal,valType);
        } catch (ClassNotFoundException ex) {
            TILRef.logError("Could not find class name {} when parsing a generic object from Tag!",className,ex);
        }
        return null;
    }

    /**
     * This only handles primitive types for the most part, so it may be better to just do whatever you want manually
     */
    @SuppressWarnings("deprecation")
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

    private static List<?> readFromList(ListTagAPI list) {
        List<Object> ret = new ArrayList<>();
        for(BaseTagAPI based : list.iterable()) {
            if(based.isCompound()) ret.add(parseGenericFromTag(based.asCompoundTag()));
            else return null;
        }
        return ret;
    }

    public static void writeGenericToTag(CompoundTagAPI tag, Object obj) {
        tag.putString("type",obj.getClass().getName());
        if(obj instanceof List<?>) tag.putTag("value",writeList(obj));
        else tag.putString("value",obj.toString());
    }

    private static ListTagAPI writeList(Object obj) {
        ListTagAPI list = TagHelper.makeListTag();
        if(Objects.nonNull(list)) {
            List<?> val = (List<?>) obj;
            for(Object element : val) {
                CompoundTagAPI tag = TagHelper.makeCompoundTag();
                if(Objects.nonNull(tag)) {
                    tag.putString("type", element.getClass().getName());
                    if(element instanceof List<?>) tag.putTag("value",writeList(element));
                    else tag.putString("value",element.toString());
                    list.addTag(tag);
                }
            }
        }
        return list;
    }
}
