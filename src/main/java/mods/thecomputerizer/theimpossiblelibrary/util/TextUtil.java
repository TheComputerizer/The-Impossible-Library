package mods.thecomputerizer.theimpossiblelibrary.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TextUtil {

    public static String compileCollection(Collection<String> collection) {
        return compileCollection(collection.toArray(new String[0]));
    }

    public static String compileCollection(String[] collection) {
        StringBuilder builder = new StringBuilder();
        builder.append("[ ");
        for(String element : collection) builder.append("\"").append(element).append("\" ");
        builder.append("]");
        return builder.toString();
    }

    public static List<String> splitToList(String original, String splitBy) {
        return Arrays.stream(original.split(splitBy)).collect(Collectors.toList());
    }

    public static List<String> splitToList(String original, String splitBy, int limit) {
        return Arrays.stream(original.split(splitBy, limit)).collect(Collectors.toList());
    }
}
