package mods.thecomputerizer.theimpossiblelibrary.api.util;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Uncategorized util methods
 */
@SuppressWarnings("unused")
public class Misc {

    public static boolean allNonNull(Object ... objects) {
        return !anyNull(objects);
    }

    public static boolean allNull(Object ... objects) {
        return !anyNonNull(objects);
    }

    public static boolean anyNonNull(Object ... objects) {
        for(Object obj : objects)
            if(Objects.nonNull(obj)) return true;
        return false;
    }

    public static boolean anyNull(Object ... objects) {
        for(Object obj : objects)
            if(Objects.isNull(obj)) return true;
        return false;
    }

    /**
     * Exceptions need to be handled externally
     */
    public static <K,V> @Nullable V applyNullable(@Nullable K thing, Function<K,V> function) {
        return Objects.isNull(thing) ? null : function.apply(thing);
    }

    /**
     * Exceptions need to be handled externally
     */
    public static <K> void consumeNullable(@Nullable K thing, Consumer<K> conumer) {
        if(Objects.nonNull(thing)) conumer.accept(thing);
    }
    
    @SafeVarargs public static <T> boolean equalsAny(T thing, T ... others) {
        for(T other : others)
            if(equalsNullable(thing,other)) return true;
        return false;
    }
    
    public static boolean equalsAnyIgnoreCase(String s1, String ... others) {
        for(String other : others)
            if(equalsNullableIgnoreCase(s1,other)) return true;
        return false;
    }
    
    public static <T> boolean equalsNullable(@Nullable T thing, @Nullable Object other) {
        return Objects.isNull(thing) ? Objects.isNull(other) : Objects.nonNull(other) && thing.equals(other);
    }
    
    public static <T> boolean equalsNullableIgnoreCase(@Nullable String s1, @Nullable String s2) {
        return Objects.isNull(s1) ? Objects.isNull(s2) : Objects.nonNull(s2) && s1.equalsIgnoreCase(s2);
    }

    /**
     * Replaces file paths in a list that point to folders with a list of all files in that folder. File paths that
     * point to single files are still added file paths that don't exist are automatically removed
     */
    public static List<String> expandFilePaths(@Nullable List<String> filePaths) {
        Set<String> files = new HashSet<>();
        if(Objects.nonNull(filePaths)) {
            for(String filePath : filePaths) {
                try(Stream<Path> paths = Files.walk(Paths.get(filePath))) {
                    files.addAll(paths.filter(Files::isRegularFile).map(Path::toString).collect(Collectors.toList()));
                } catch(IOException ex) {
                    TILRef.logError("Failed to walk file path {}",filePath,ex);
                }
                File file = new File(filePath);
                if(filePath.contains(".")) files.add(filePath);
                else {
                    String[] nextFiles = file.list();
                    if(Objects.nonNull(nextFiles)){
                        for(String appendedPath : expandFilePaths(nextFiles))
                            files.add(filePath+"/"+appendedPath);
                    }
                }
            }
        }
        return new ArrayList<>(files);
    }

    public static List<String> expandFilePaths(String ... filePaths) {
        return expandFilePaths(Arrays.asList(filePaths));
    }

    /**
     * Fixes instances of Object[] that come from generic parsing.
     */
    public static Object getFixedObject(Object obj, Class<?> fixAs) {
        if(obj instanceof Object[]) return ArrayHelper.fixObjParsed((Object[])obj,fixAs);
        try {
            return fixAs.cast(obj);
        } catch(ClassCastException ex) {
            return obj;
        }
    }

    public static <V> V getEither(boolean choice, V ifChoice, V notChoice) {
        return choice ? ifChoice : notChoice;
    }

    /**
     * if choice 1 else choice 2 else neither
     */
    public static <V> V getEitherOr(boolean choice1, boolean choice2, V ifChoice1, V ifChoice2, V neither) {
        return choice1 ? ifChoice1 : (choice2 ? ifChoice2 : neither);
    }
    
    @SafeVarargs
    public static <V> V getEitherTrailing(boolean[] choices, V ... returns) {
        if(returns.length==1) return returns[0];
        for(int i=0; i+1<returns.length; i++) {
            boolean choice = choices.length>i && choices[i];
            if(choice) return returns[i];
        }
        return returns[returns.length-1];
    }

    public static String getLastSplit(String str, char splitBy) {
        return str.substring(str.lastIndexOf(splitBy)+1);
    }

    public static String getLastSplit(String str, String splitBy) {
        return str.substring(str.lastIndexOf(splitBy)+1);
    }

    public static <N,V> V getNullable(@Nullable N nullable, V notNull, V isNull) {
        return getEither(Objects.nonNull(nullable),notNull,isNull);
    }

    /**
     * Adds a trimmed lowercase string a collection after ensuring it is not null, empty, or blank
     */
    public static void lowerCaseAddCollection(Collection<String> c, String str) {
        if(TextHelper.isNotBlank(str)) c.add(str.trim().toLowerCase());
    }

    /**
     * Adds a trimmed lowercase string a map key after ensuring it is not null, empty, or blank
     */
    public static <V> void lowerCaseAddMap(Map<String,V> map, String str, V val) {
        if(TextHelper.isNotBlank(str)) map.put(str.trim().toLowerCase(),val);
    }

    public static String removeAll(String str, String ... removals) {
        for(String removal : removals) str = str.replaceAll(removal,"");
        return str;
    }
    
    public static <V,W> BiConsumer<V,W> safeBiConsumer(BiConsumer<V,W> biConsumer) {
        return safeBiConsumer(biConsumer,t -> {
            throw new RuntimeException("Consumer handle failed!",t);
        });
    }
    
    public static <V,W> BiConsumer<V,W> safeBiConsumer(BiConsumer<V,W> biConsumer, Consumer<Throwable> onThrow) {
        return (val1,val2) -> {
            try {
                biConsumer.accept(val1,val2);
            } catch(Throwable t) {
                onThrow.accept(t);
            }
        };
    }
    
    public static <A,B,V> BiFunction<A,B,V> safeBiFunction(BiFunction<A,B,V> biFunction) {
        return safeBiFunction(biFunction,t -> {
            throw new RuntimeException("BiFunction handle failed!",t);
        });
    }
    
    public static <A,B,V> BiFunction<A,B,V> safeBiFunction(BiFunction<A,B,V> biFunction, Function<Throwable,V> onThrow) {
        return (arg1,arg2) -> {
            try {
                return biFunction.apply(arg1,arg2);
            } catch(Throwable t) {
                return onThrow.apply(t);
            }
        };
    }
    
    public static <V> Consumer<V> safeConsumer(Consumer<V> consumer) {
        return safeConsumer(consumer,t -> {
            throw new RuntimeException("Consumer handle failed!",t);
        });
    }
    
    public static <V> Consumer<V> safeConsumer(Consumer<V> consumer, Consumer<Throwable> onThrow) {
        return value -> {
            try {
                consumer.accept(value);
            } catch(Throwable t) {
                onThrow.accept(t);
            }
        };
    }
    
    public static <A,V> Function<A,V> safeFunction(Function<A,V> function) {
        return safeFunction(function,t -> {
            throw new RuntimeException("Function handle failed!",t);
        });
    }
    
    public static <A,V> Function<A,V> safeFunction(Function<A,V> function, Function<Throwable,V> onThrow) {
        return arg -> {
            try {
                return function.apply(arg);
            } catch(Throwable t) {
                return onThrow.apply(t);
            }
        };
    }
    
    public static <V> Supplier<V> safeSupplier(Supplier<V> supplier) {
        return safeSupplier(supplier,t -> {
            throw new RuntimeException("Supplier handle failed!",t);
        });
    }
    
    public static <V> Supplier<V> safeSupplier(Supplier<V> supplier, Function<Throwable,V> onThrow) {
        return () -> {
            try {
                return supplier.get();
            } catch(Throwable t) {
                return onThrow.apply(t);
            }
        };
    }
    
    public static String[] wordCombinations(String word1, String word2, String ... separators) {
        return wordCombinations(new String[]{word1,word2},separators);
    }
    
    public static String[] wordCombinations(String[] words, String ... separators) {
        if(Objects.isNull(words)) return new String[]{};
        if(words.length==0) return words;
        String[] nonBlankWords = new String[words.length];
        int nonBlankIndex = 0;
        for(String word : words) {
            if(TextHelper.isNotBlank(word)) {
                nonBlankWords[nonBlankIndex] = word;
                nonBlankIndex++;
            }
        }
        switch(nonBlankIndex) {
            case 0: return new String[]{};
            case 1: return new String[]{nonBlankWords[0]};
            default: {
                if(Objects.isNull(separators) || separators.length==0) separators = new String[]{" "};
                return wordCombinationsVerified(Arrays.copyOfRange(nonBlankWords,0,nonBlankIndex),separators);
            }
        }
    }
    
    /**
     * Assumes the input words array is not null, does not contain any blank strings, and contains at least 2 elements.
     * Assumes there is at least 1 separator.
     */
    private static String[] wordCombinationsVerified(String[] words, String ... separators) {
        String[] combined = new String[separators.length*2];
        for(int i=0;i<separators.length;i++) {
            String separator = separators[i];
            StringJoiner joiner = new StringJoiner(Objects.nonNull(separator) ? separator : " ");
            for(String word : words) joiner.add(word);
            combined[i] = joiner.toString();
        }
        return combined;
    }

    public static <V,W> @Nullable W wrap(@Nullable V val, Function<V,W> wrapperFunc) {
        return applyNullable(val,wrapperFunc);
    }
    
    public static <V> boolean xOr(V value, V in1, V in2) {
        return value.equals(in1)!=value.equals(in2);
    }
    
    public static <V> V xOr(V value, V in1, V in2, V on, V off) {
        return xOr(value,in1,in2) ? on : off;
    }
}