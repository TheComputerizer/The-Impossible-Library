package mods.thecomputerizer.theimpossiblelibrary.api;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Base reference API for global constants specific to this library
 */
public class TILRef {

    public static final File DATA_DIRECTORY = new File("./impossible_data");
    public static final Logger LOGGER = LogManager.getLogger("The Impossible Library");
    public static final String MODID = "theimpossiblelibrary";
    public static final String NAME = "The Impossible Library";
    public static final String VERSION = "0.4.0";
    private static CommonAPI API;
    /**
     * Enable to disable server stuff
     */
    public static boolean CLIENT_ONLY;
    private static Reference INSTANCE;

    /**
     * Should only exist on the client
     */
    public static @Nullable ClientAPI getClientAPI() {
        if(Objects.nonNull(INSTANCE)) {
            if(INSTANCE.isClient()) return (ClientAPI)API;
            logError("The client API can only be retrieved from the client side!");
            return null;
        }
        logError("Cannot get the client API until the reference API has been initialized!");
        return null;
    }

    public static <A> @Nullable A getClientSubAPI(String name, Function<ClientAPI,A> getter) {
        if(Objects.isNull(INSTANCE) || !INSTANCE.isClient()) {
            logError("Cannot get client sub API {} since this is not the client side!");
            return null;
        }
        if(Objects.nonNull(API)) return getter.apply((ClientAPI)API);
        logError("Cannot get client sub API {} since the client API is null!",name);
        return null;
    }

    public static @Nullable CommonAPI getCommonAPI() {
        if(Objects.nonNull(INSTANCE)) return API;
        logError("Cannot get the common API until the reference API has been initialized!");
        return null;
    }

    public static <A> @Nullable A getCommonSubAPI(String name, Function<CommonAPI,A> getter) {
        if(Objects.nonNull(API)) return getter.apply(API);
        logError("Cannot get common sub API {} since the common API is null!",name);
        return null;
    }

    /**
     * Initializes the base reference API
     */
    public static Reference instance(Supplier<Boolean> isClient, String dependencies) {
        if(Objects.isNull(INSTANCE)) {
            INSTANCE = new Reference(isClient.get(),dependencies,MODID,NAME,VERSION);
        }
        return INSTANCE;
    }

    public static void log(Level level, String msg, Object ... args) {
        logNullable(level,msg,args);
    }

    public static void logAll(String msg, Object ... args) {
        logNullable(Level.ALL,msg,args);
    }

    public static void logDebug(String msg, Object ... args) {
        logNullable(Level.DEBUG,msg,args);
    }

    public static void logError(String msg, Object ... args) {
        logNullable(Level.ERROR,msg,args);
    }

    public static void logFatal(String msg, Object ... args) {
        logNullable(Level.FATAL,msg,args);
    }

    public static void logInfo(String msg, Object ... args) {
        logNullable(Level.INFO,msg,args);
    }

    private static void logNullable(Level level, String msg, Object ... args) {
        if(Objects.nonNull(INSTANCE)) INSTANCE.log(level,msg,args);
        else LOGGER.log(level,msg,args);
    }

    public static void logOff(String msg, Object ... args) {
        logNullable(Level.OFF,msg,args);
    }

    public static void logTrace(String msg, Object ... args) {
        logNullable(Level.TRACE,msg,args);
    }

    public static void logWarn(String msg, Object ... args) {
        logNullable(Level.WARN,msg,args);
    }

    public static @Nullable ResourceLocationAPI<?> res(String path) {
        if(Objects.nonNull(INSTANCE)) return INSTANCE.getResource(path);
        logError("Cannot get a ResourceLocation until the reference API has been initialized!");
        return null;
    }

    public static void setAPI(CommonAPI api) {
        if(Objects.isNull(INSTANCE))
            logError("Cannot set the API until the reference API has been initialized!");
        else API = api;
    }
}
