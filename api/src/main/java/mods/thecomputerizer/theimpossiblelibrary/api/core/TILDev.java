package mods.thecomputerizer.theimpossiblelibrary.api.core; //TODO move to dedicated dev package under core package

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;
import static org.apache.logging.log4j.Level.*;

/**
 * Constants that need to be loaded after a CoreAPI instance is created should be stored in TILDevCoreDependent
 */
public class TILDev {
    
    public static final boolean DEBUG_NETWORK = getBooleanProperty("debug.network"); //`-Dtil.debug.network=true`
    public static final boolean DEV = getBooleanProperty("dev"); //`-Dtil.dev=true`
    public static final Set<String> CLASSPATH_COREMODS = parseClasspathMods(true); //`-Dtil.classpath.coremods=...`
    public static final Set<String> CLASSPATH_MODS = parseClasspathMods(false); //`-Dtil.classpath.mods=...`
    static final String JAR_NAME = MODID+"-"+VERSION+".jar";
    private static final Logger LOGGER = DEV ? LogManager.getLogger("TIL DEV") : null;
    
    private static Path devClasses;
    private static Path devResources;
    
    private static boolean getBooleanProperty(String propertyName) {
        return Boolean.parseBoolean(getProperty(propertyName));
    }
    
    public static void checkDevPath(Path path) {
        if(DEV && Objects.isNull(devClasses) && isLoaderPath(path)) setDevPaths(path);
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
    
    public static Path getLoaderResourcePath(String ... paths) {
        if(Objects.isNull(devResources) || !devResources.toFile().exists()) {
            TILRef.logError("Cannot get path from null or nonexistant dev resources path! ({})",devResources);
            return null;
        }
        boolean classes = paths[paths.length-1].endsWith(".class");
        Path result = classes ? devClasses : devResources;
        for(String path : paths)
            result = result.resolve(path);
        return result;
    }
    
    private static String getProperty(String propertyName) {
        return System.getProperty("til."+propertyName);
    }
    
    @SuppressWarnings("SameParameterValue")
    static String getProperty(String propertyName, String defaultValue) {
        return System.getProperty("til."+propertyName,defaultValue);
    }
    
    @IndirectCallers
    public static boolean isLoaderFile(File file) {
        return isLoaderName(file.getName());
    }
    
    public static boolean isLoaderName(String fileName) {
        return JAR_NAME.equals(fileName);
    }
    
    public static boolean isLoaderPath(Path path) {
        return TILDevCoreDependent.isLoaderPath(path);
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
    
    public static void setDevPaths(Path classes) {
        devClasses = classes;
        TILRef.logInfo("Set devClasses to {}",devClasses);
        File file = classes.toFile();
        if(file.isDirectory()) {
            Path build = classes.getParent().getParent().getParent();
            devResources = Paths.get(build.toAbsolutePath().toString(),"resources",sourceName());
        } else devResources = classes;
        TILRef.logInfo("Set devResources to {}",devResources);
    }
    
    /**
     * The main source is likely being used for the classes and shouldn't be assumed.
     * All necessary loader-specific resources are in their respective sources.
     */
    static String sourceName() {
        String loader = CoreAPI.getModLoaderName();
        return "legacy".equals(loader) ? "main" : "til"+loader;
    }
}
