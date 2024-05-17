package mods.thecomputerizer.theimpossiblelibrary.api.text;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class TextHelper {

    public static String arrayToString(Object ... array) {
        return arrayToString(0,System.lineSeparator(),array);
    }

    public static String arrayToString(int limit, Object ... array) {
        return arrayToString(limit,System.lineSeparator(),array);
    }

    public static String arrayToString(String split, Object ... array) {
        return arrayToString(0,split,array);
    }

    public static String arrayToString(int limit, String split, Object ... array) {
        if(Objects.isNull(array) || array.length==0) return null;
        limit = limit>0 ? limit : Integer.MAX_VALUE;
        StringBuilder builder = new StringBuilder();
        int index = 1;
        for(Object element : array) {
            if (Objects.nonNull(element)) {
                String asString = element.toString();
                if (!asString.trim().isEmpty())
                    builder.append(asString);
            }
            if(index<array.length && index<limit) {
                builder.append(split);
                index++;
            }
            else if(index==limit) return builder.toString();
        }
        return builder.toString();
    }

    public static String capitalize(String original) {
        if(Objects.isNull(original) || original.isEmpty()) return original;
        return String.valueOf(original.charAt(0)).toUpperCase()+original.substring(1).toLowerCase();
    }

    /**
     * Converts a collection of generic objects into a single string of the format [ "element1" "element2" "element3" ... ]
     */
    public static String compileCollection(Collection<?> collection) {
        return compileCollection(collection.toArray(new Object[0]));
    }

    /**
     * Converts an array of generic objects into a single string of the format [ element1 element2 element3 ... ]
     * If the objects are strings, the elements will be in the format [ "element1" "element2" "element3" ... ]
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

    public static TextHelperAPI<?> getHelper() {
        return TILRef.getCommonSubAPI(CommonAPI::getTextHelper);
    }

    public static TextStringAPI<?> getLiteral(String text) {
        return getHelper().getLiteral(text);
    }

    public static TextTranslationAPI<?> getTranslated(String key, Object ... args) {
        return getHelper().getTranslated(key,args);
    }

    /**
     * Converts an iterable of strings to a single string with newline characters with an optional limiter. The limit
     * input determines the maximum number of elements that can be read in before it gets cut off. Setting the limit to zero
     * or below will disable it. Returns null if the input list is empty or null. If an element in the input list is
     * empty, null, or has only whitespace, it will be replaced with a newline character or be removed if it is the final
     * element.
     */
    public static String fromIterable(Iterable<String> itr) {
        return fromIterable(itr,0,System.lineSeparator());
    }

    public static String fromIterable(Iterable<String> itr, int limit) {
        return fromIterable(itr,limit,System.lineSeparator());
    }

    public static String fromIterable(Iterable<String> itr, String split) {
        return fromIterable(itr,0,split);
    }

    public static String fromIterable(Iterable<String> itr, int limit, String split) {
        if(Objects.isNull(itr)) return null;
        int count = 0;
        StringJoiner joiner = new StringJoiner(split);
        for(String str : itr) {
            joiner.add(str);
            if(limit>0) {
                count++;
                if(count>=limit) break;
            }
        }
        String val = joiner.toString();
        return StringUtils.isNotEmpty(val) ? val : null;
    }

    /**
     * Assumes the input string is camel case
     */
    public static String makeCaseTypeFromCamel(String original, TextCasing type) {
        String[] words = TextCasing.CAMEL.split(original);
        if(type==TextCasing.CAMEL) return TextCasing.CAMEL.combine(words);
        if(type==TextCasing.PASCAL) return TextCasing.PASCAL.combine(words);
        if(type==TextCasing.SNAKE) return TextCasing.SNAKE.combine(words);
        return TextCasing.KEBAB.combine(words);
    }

    /**
     * Assumes the input string is kebab case
     */
    public static String makeCaseTypeFromKebab(String original, TextCasing type) {
        String[] words = TextCasing.KEBAB.split(original);
        if(type==TextCasing.CAMEL) return TextCasing.CAMEL.combine(words);
        if(type==TextCasing.PASCAL) return TextCasing.PASCAL.combine(words);
        if(type==TextCasing.SNAKE) return TextCasing.SNAKE.combine(words);
        return TextCasing.KEBAB.combine(words);
    }

    /**
     * Assumes the input string is pascal case
     */
    public static String makeCaseTypeFromPascal(String original, TextCasing type) {
        String[] words = TextCasing.PASCAL.split(original);
        if(type==TextCasing.CAMEL) return TextCasing.CAMEL.combine(words);
        if(type==TextCasing.PASCAL) return TextCasing.PASCAL.combine(words);
        if(type==TextCasing.SNAKE) return TextCasing.SNAKE.combine(words);
        return TextCasing.KEBAB.combine(words);
    }

    /**
     * Assumes the input string is snake case
     */
    public static String makeCaseTypeFromSnake(String original, TextCasing type) {
        String[] words = TextCasing.SNAKE.split(original);
        if(type==TextCasing.CAMEL) return TextCasing.CAMEL.combine(words);
        if(type==TextCasing.PASCAL) return TextCasing.PASCAL.combine(words);
        if(type==TextCasing.SNAKE) return TextCasing.SNAKE.combine(words);
        return TextCasing.KEBAB.combine(words);
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
     * Implementation of String#repeat for the versions that rely on Java 8
     */
    public static String repeat(String base, int num) {
        StringBuilder builder = new StringBuilder();
        for(int i=0; i<num; i++) builder.append(base);
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
     * Returns the input string with the specified number of leading tabs
     */
    public static String withTabs(String original, int tabs) {
        StringBuilder builder = new StringBuilder();
        for(int i=0; i <tabs; i++) builder.append("\t");
        return builder.append(original).toString();
    }

    public enum TextCasing {

        CAMEL("camel", (input) -> input.split("(?=\\p{Upper})"), (words) -> {
            StringBuilder builder = new StringBuilder();
            if(Objects.isNull(words) || words.length==0) return builder.toString();
            for(int i=0;i<words.length;i++) {
                if(i==0) builder.append(words[i].toLowerCase());
                else builder.append(capitalize(words[i]));
            }
            return builder.toString();
        }),
        PASCAL("pascal", (input) -> input.split("(?<=.)(?=\\p{Upper})"), (words) -> {
            StringBuilder builder = new StringBuilder();
            if(Objects.isNull(words)) return builder.toString();
            for(String word : words)
                builder.append(capitalize(word));
            return builder.toString();
        }),
        SNAKE("snake", (input) -> input.split("_"), (words) -> {
            StringBuilder builder = new StringBuilder();
            if(Objects.isNull(words) || words.length==0) return builder.toString();
            for(int i=0;i<words.length;i++) {
                builder.append(words[i].toLowerCase());
                if(i<words.length-1) builder.append("_");
            }
            return builder.toString();
        }),
        KEBAB("kebab", (input) -> input.split("-"), (words) -> {
            StringBuilder builder = new StringBuilder();
            if(Objects.isNull(words) || words.length==0) return builder.toString();
            for(int i=0;i<words.length;i++) {
                builder.append(words[i].toLowerCase());
                if(i<words.length-1) builder.append("-");
            }
            return builder.toString();
        });

        @Getter private final String name;
        private final Function<String,String[]> splitFunc;
        private final Function<String[],String> combineFunc;
        TextCasing(String name, Function<String,String[]> splitFunc, Function<String[],String> combineFunc) {
            this.name = name;
            this.splitFunc = splitFunc;
            this.combineFunc = combineFunc;
        }

        public String[] split(String input) {
            return this.splitFunc.apply(input);
        }

        public String combine(String ... words) {
            return this.combineFunc.apply(words);
        }

        public static final Map<String, TextCasing> BY_NAME = new HashMap<>();

        static {
            for (TextCasing casing : values())
                BY_NAME.putIfAbsent(casing.name,casing);
        }
    }
}