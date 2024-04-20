package mods.thecomputerizer.theimpossiblelibrary.api.core;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TILDev {

    public static final boolean DEV = Boolean.parseBoolean(System.getProperty("tilDev")); //`-DtilDev=true`
    private static final Logger LOGGER = DEV ? LogManager.getLogger("TIL DEV") : null;
    public static final Set<String> CLASSPATH_COREMODS = parseClasspathMods(System.getProperty("tilClassPathCoreMods"));
    public static final Set<String> CLASSPATH_MODS = parseClasspathMods(System.getProperty("tilClassPathMods"));

    private static Set<String> parseClasspathMods(String mods) {
        String[] split = Objects.nonNull(mods) ? mods.split(";") : null;
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(Objects.nonNull(split) ? split : new String[0])));
    }

    public static void log(Level level, String msg, Object ... args) {
        if(DEV) LOGGER.log(level,msg,args);
    }

    public static void logAll(String msg, Object ... args) {
        log(Level.ALL,msg,args);
    }

    public static void logDebug(String msg, Object ... args) {
        log(Level.DEBUG,msg,args);
    }

    public static void logError(String msg, Object ... args) {
        log(Level.ERROR,msg,args);
    }

    public static void logFatal(String msg, Object ... args) {
        log(Level.FATAL,msg,args);
    }

    public static void logInfo(String msg, Object ... args) {
        log(Level.INFO,msg,args);
    }

    public static void logOff(String msg, Object ... args) {
        log(Level.OFF,msg,args);
    }

    public static void logTrace(String msg, Object ... args) {
        log(Level.TRACE,msg,args);
    }

    public static void logWarn(String msg, Object ... args) {
        log(Level.WARN,msg,args);
    }

    public static <I> void devConsume(I input, Consumer<I> consumer) {
        if(DEV) consumer.accept(input);
    }

    public static <I,R> @Nullable R devFunc(I input, Function<I,R> function) {
        return DEV ? function.apply(input) : null;
    }

    public static <R> @Nullable R devSupply(Supplier<R> supplier) {
        return DEV ? supplier.get() : null;
    }
}
