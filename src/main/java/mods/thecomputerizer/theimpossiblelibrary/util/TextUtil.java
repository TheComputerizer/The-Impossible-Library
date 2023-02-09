package mods.thecomputerizer.theimpossiblelibrary.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TextUtil {

    /**
     * Converts a collection of generic objects into a single string of the format [ "element1" "element2" "element3" ... ]
     */
    public static String compileCollection(Collection<?> collection) {
        return compileCollection(collection.toArray(new Object[0]));
    }

    /**
     * Converts an array of generic objects into a single string of the format [ element1 element2 element3 ... ]
     * If the objects are strings the elements will be in the format [ "element1" "element2" "element3" ... ]
     */
    public static String compileCollection(Object ... generics) {
        StringBuilder builder = new StringBuilder();
        builder.append("[ ");
        for(Object element : generics) {
            if(element instanceof String)
                builder.append("\"").append(element).append("\" ");
            else builder.append(element);
        }
        builder.append("]");
        return builder.toString();
    }

    /**
     * Splits a string into a list of strings based on the input separator
     */
    public static List<String> splitToList(String original, String splitBy) {
        return Arrays.stream(original.split(splitBy)).collect(Collectors.toList());
    }

    /**
     * Same as the above method with a limit on the maximum number of elements.
     */
    public static List<String> splitToList(String original, String splitBy, int limit) {
        return Arrays.stream(original.split(splitBy, limit)).collect(Collectors.toList());
    }

    /**
     * Splits a string into a list of strings based on the system line separator
     */
    public static List<String> newLineSplit(String original) {
        return Arrays.stream(original.split(System.lineSeparator())).collect(Collectors.toList());
    }

    /**
     * Same as the above method with a limit on the maximum number of elements.
     */
    public static List<String> newLineSplit(String original, int limit) {
        return Arrays.stream(original.split(System.lineSeparator(), limit)).collect(Collectors.toList());
    }

    /**
     * Converts a list of strings to a single string with newline characters with an optional limiter. The limit
     * input determines the maximum number of elements that can be read in before it gets cut off. Setting the limit to 0
     * or below will disable it. Returns null if the input list is empty or null. If an element in the input list is
     * empty, null, or has only whitespace it will be replaced with a newline character or be removed if it is the final
     * element.
     */
    public static String listToString(Collection<String> list, int limit) {
        if(Objects.isNull(list) || list.isEmpty()) return null;
        limit = limit>0 ? limit : Integer.MAX_VALUE;
        StringBuilder builder = new StringBuilder();
        int index = 1;
        for(String element : list) {
            if (Objects.nonNull(element)) {
                if (!element.trim().isEmpty()) {
                    builder.append(element);
                }
            }
            if(index<list.size() && index<limit) {
                builder.append(System.lineSeparator());
                index++;
            }
            else if(index==limit) return builder.toString();
        }
        return builder.toString();
    }

    /**
     * Returns the input string with the specified number of leading tabs
     */
    public static String withTabs(String original, int tabs) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tabs; i++) builder.append("\t");
        return builder.append(original).toString();
    }
}
