package mods.thecomputerizer.theimpossiblelibrary.api.core;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;
import static org.apache.logging.log4j.Level.*;

@SuppressWarnings("unused") public class TILDev {

    public static final boolean DEV = Boolean.parseBoolean(System.getProperty("til.dev")); //`-Dtil.dev=true`
    private static final String LOADER_FILE = System.getProperty("til.classpath.file",MODID+"-"+VERSION+".jar");
    private static final Logger LOGGER = DEV ? LogManager.getLogger("TIL DEV") : null;
    public static final Set<String> CLASSPATH_COREMODS = parseClasspathMods(System.getProperty("til.classpath.coremods"));
    public static final Set<String> CLASSPATH_MODS = parseClasspathMods(System.getProperty("til.classpath.mods"));
    
    public static <I> void devConsume(I input, Consumer<I> consumer) {
        if(DEV) consumer.accept(input);
    }
    
    public static <I,R> @Nullable R devFunc(I input, Function<I,R> function) {
        return DEV ? function.apply(input) : null;
    }
    
    public static <R> @Nullable R devSupply(Supplier<R> supplier) {
        return DEV ? supplier.get() : null;
    }
    
    /**
     * When used as a local dependency, the file needs to be remapped which can confuse the loader's auto-detection.
     * The loader file can be set manually via -Dtil.classpath.file
     */
    public static boolean isLoader(String fileName) {
        return LOADER_FILE.equals(fileName);
    }

    public static void log(Level level, String msg, Object ... args) {
        if(DEV) LOGGER.log(level,msg,args);
    }

    public static void logDebug(String msg, Object ... args) {
        log(DEBUG,msg,args);
    }

    public static void logError(String msg, Object ... args) {
        log(ERROR,msg,args);
    }

    public static void logFatal(String msg, Object ... args) {
        log(FATAL,msg,args);
    }
    
    public static void logFromASM(String msg, Object arg) {
        logError(msg,arg);
    }

    public static void logInfo(String msg, Object ... args) {
        log(INFO,msg,args);
    }

    public static void logTrace(String msg, Object ... args) {
        log(TRACE,msg,args);
    }

    public static void logWarn(String msg, Object ... args) {
        log(WARN,msg,args);
    }
    
    private static Set<String> parseClasspathMods(String mods) {
        String[] split = Objects.nonNull(mods) ? mods.split(";") : null;
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(Objects.nonNull(split) ? split : new String[0])));
    }
}
