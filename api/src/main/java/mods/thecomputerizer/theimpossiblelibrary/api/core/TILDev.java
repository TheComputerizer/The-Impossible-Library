package mods.thecomputerizer.theimpossiblelibrary.api.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;
import static org.apache.logging.log4j.Level.*;

public class TILDev {
    
    public static final boolean DEBUG_NETWORK = getBooleanProperty("debug.network"); //`-Dtil.debug.network=true`
    public static final boolean DEV = getBooleanProperty("dev"); //`-Dtil.dev=true`
    public static final Set<String> CLASSPATH_COREMODS = parseClasspathMods(true); //`-Dtil.classpath.coremods=...`
    public static final Set<String> CLASSPATH_MODS = parseClasspathMods(false); //`-Dtil.classpath.mods=...`
    private static final String JAR_NAME = MODID+"-"+VERSION+".jar";
    private static final String LOADER_FILE = getProperty("classpath.file",JAR_NAME); //`-Dtil.classpath.file=...`
    private static final Logger LOGGER = DEV ? LogManager.getLogger("TIL DEV") : null;
    
    private static boolean getBooleanProperty(String propertyName) {
        return Boolean.parseBoolean(getProperty(propertyName));
    }
    
    private static String getProperty(String propertyName) {
        return System.getProperty("til."+propertyName);
    }
    
    @SuppressWarnings("SameParameterValue")
    private static String getProperty(String propertyName, String defaultValue) {
        return System.getProperty("til."+propertyName,defaultValue);
    }
    
    @IndirectCallers
    public static <I> void devConsume(I input, Consumer<I> consumer) {
        if(DEV) consumer.accept(input);
    }
    
    @IndirectCallers
    public static <I,R> @Nullable R devFunc(I input, Function<I,R> function) {
        return DEV ? function.apply(input) : null;
    }
    
    @IndirectCallers
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
    
    @IndirectCallers
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
    
    private static Set<String> parseClasspathMods(boolean coremods) {
        String mods = getProperty("classpath."+(coremods ? "coremods" : "mods"));
        if(Objects.isNull(mods)) return Collections.emptySet();
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(mods.split(";"))));
    }
}
