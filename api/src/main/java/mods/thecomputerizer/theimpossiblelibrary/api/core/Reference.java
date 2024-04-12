package mods.thecomputerizer.theimpossiblelibrary.api.core;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Global constants API.
 */
@Getter
public class Reference {

    private static String makeDefModid(String name) {
        return name.replaceAll(" ","").toLowerCase();
    }

    private final boolean client;
    private final String dependencies;
    private final Logger logger;
    private final String modid;
    private final String name;
    private final String version;

    protected Reference(boolean client, String name, String version) {
        this(client,"",LogManager.getLogger(name),makeDefModid(name),name,version);
    }

    protected Reference(boolean client, @Nullable String dependencies, @Nullable String modid,
                        @Nullable String name, @Nullable String version) {
        this(client,dependencies,LogManager.getLogger(name),modid,name,version);
    }

    protected Reference(boolean client, @Nullable String dependencies, @Nullable Logger logger,
                        @Nullable String modid, @Nullable String name, @Nullable String version) {
        this.client = client;
        this.dependencies = Objects.nonNull(dependencies) ? dependencies : "";
        this.logger = Objects.nonNull(logger) ? logger : LogManager.getLogger("TIL REF");
        this.modid = StringUtils.isNotBlank(modid) ? modid : "modid";
        this.name = StringUtils.isNotBlank(name) ? name : "Name";
        this.version = StringUtils.isNotBlank(version) ? version : "0.0.1";
    }

    public @Nullable ResourceLocationAPI<?> getResource(String path) {
        return ResourceHelper.getResource(this.modid,path);
    }

    public void log(Level level, String msg, Object ... args) {
        this.logger.log(level,msg,args);
    }

    public void logAll(String msg, Object ... args) {
        this.logger.log(Level.ALL,msg,args);
    }

    public void logDebug(String msg, Object ... args) {
        this.logger.debug(msg,args);
    }

    public void logError(String msg, Object ... args) {
        this.logger.error(msg,args);
    }

    public void logFatal(String msg, Object ... args) {
        this.logger.fatal(msg,args);
    }

    public void logInfo(String msg, Object ... args) {
        this.logger.info(msg,args);
    }

    public void logOff(String msg, Object ... args) {
        this.logger.log(Level.OFF,msg,args);
    }

    public void logTrace(String msg, Object ... args) {
        this.logger.trace(msg,args);
    }

    public void logWarn(String msg, Object ... args) {
        this.logger.warn(msg,args);
    }
}
