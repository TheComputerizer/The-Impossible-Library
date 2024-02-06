package mods.thecomputerizer.theimpossiblelibrary.api;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Global constants API. The RES generic is used to set the ResourceLocation/Identifier type dependign on the loader
 */
public abstract class ReferenceAPI<RES> {

    public static boolean CLIENT = true;
    public static String DEPENDENCIES = "";
    public static Logger LOGGER = LogManager.getLogger("TIL API");
    public static String MODID = "modid";
    public static String NAME = "Name";
    public static String VERSION = "0.0.1";

    private static ReferenceAPI<?> INSTANCE;

    private static void callNullableReference(Consumer<ReferenceAPI<?>> caller) {
        if(Objects.nonNull(INSTANCE)) caller.accept(INSTANCE);
    }

    public static void logAll(String msg, Object ... args) {
        logLevel(Level.ALL,msg,args);
    }

    public static void logDebug(String msg, Object ... args) {
        logLevel(Level.DEBUG,msg,args);
    }

    public static void logError(String msg, Object ... args) {
        logLevel(Level.ERROR,msg,args);
    }

    public static void logFatal(String msg, Object ... args) {
        logLevel(Level.FATAL,msg,args);
    }

    public static void logInfo(String msg, Object ... args) {
        logLevel(Level.INFO,msg,args);
    }

    public static void logLevel(Level level, String msg, Object ... args) {
        callNullableReference(ref -> ref.log(level,msg,args));
    }

    public static void logOff(String msg, Object ... args) {
        logLevel(Level.OFF,msg,args);
    }

    public static void logTrace(String msg, Object ... args) {
        logLevel(Level.TRACE,msg,args);
    }

    public static void logWarn(String msg, Object ... args) {
        logLevel(Level.WARN,msg,args);
    }

    /**
     * Assume the reference instance has been set if this is getting called
     */
    @SuppressWarnings("unchecked")
    public static <R> R res(String path) {
        assert Objects.nonNull(INSTANCE);
        return (R)INSTANCE.getResource(path);
    }

    private final Supplier<Boolean> clientSupplier;
    private final Supplier<String> dependenciesSupplier;
    private final Supplier<String> modIDSupplier;
    private final Supplier<String> nameSupplier;
    private final Supplier<String> versionSupplier;

    protected ReferenceAPI(
            Supplier<Boolean> clientSupplier, Supplier<String> dependenciesSupplier, Supplier<String> modIDSupplier,
            Supplier<String> nameSupplier, Supplier<String> versionSupplier) {
        this.clientSupplier = clientSupplier;
        this.dependenciesSupplier = dependenciesSupplier;
        this.modIDSupplier = modIDSupplier;
        this.nameSupplier = nameSupplier;
        this.versionSupplier = versionSupplier;
    }

    public abstract RES getResource(String path);

    protected abstract void initLogger();

    public void load() {
        setIfNonNull(this.clientSupplier,b -> CLIENT = b);
        setIfNonNull(this.dependenciesSupplier,s -> DEPENDENCIES = s);
        setIfNonNull(this.modIDSupplier,s -> MODID = s);
        setIfNonNull(this.nameSupplier,s -> NAME = s);
        setIfNonNull(this.versionSupplier,s -> VERSION = s);
        initLogger();
    }

    protected void log(Level level, String msg, Object ... args) {
        LOGGER.log(level,msg,args);
    }

    private <T> void setIfNonNull(Supplier<T> getter, Consumer<T> setter) {
        if(Objects.nonNull(getter)) setter.accept(getter.get());
    }
}
