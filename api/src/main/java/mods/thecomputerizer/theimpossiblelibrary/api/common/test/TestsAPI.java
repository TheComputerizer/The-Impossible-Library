package mods.thecomputerizer.theimpossiblelibrary.api.common.test;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreStateAccessor;
import mods.thecomputerizer.theimpossiblelibrary.api.io.LoggableAPI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TestsAPI implements CoreStateAccessor, LoggableAPI {
    
    private final Logger logger;
    @Getter protected final String type;
    
    protected TestsAPI(String type) {
        this.logger = LogManager.getLogger("TIL "+type+" Tests");
        this.type = type;
    }
    
    @Override
    public void logDebug(String msg, Object ... args) {
        this.logger.debug(msg,args);
    }
    
    @Override
    public void logError(String msg, Object ... args) {
        this.logger.error(msg,args);
    }
    
    @Override
    public void logFatal(String msg, Object ... args) {
        this.logger.fatal(msg,args);
    }
    
    @Override
    public void logInfo(String msg, Object ... args) {
        this.logger.info(msg,args);
    }
    
    @Override
    public void logTrace(String msg, Object ... args) {
        this.logger.trace(msg,args);
    }
    
    @Override
    public void logWarn(String msg, Object ... args) {
        this.logger.warn(msg,args);
    }
    
    public final void runAndLog(Object ... args) {
        try {
            if(run(args)) logInfo("Ran tests successfully!");
            else logWarn("Tests failed! (args={})",args);
        } catch(Throwable t) {
            logError("Failed to run tests! (args={})",args,t);
        }
    }
    
    protected abstract boolean run(Object ... args);
}