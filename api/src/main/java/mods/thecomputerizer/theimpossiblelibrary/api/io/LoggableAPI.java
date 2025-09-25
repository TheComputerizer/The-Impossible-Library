package mods.thecomputerizer.theimpossiblelibrary.api.io;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public interface LoggableAPI {

    @IndirectCallers default void log(Level level, String msg, Object ... args) {
        if(Objects.isNull(level)) return;
        switch(level.name()) {
            case "DEBUG": {
                logDebug(msg,args);
                return;
            }
            case "ERROR": {
                logError(msg,args);
                return;
            }
            case "FATAL": {
                logFatal(msg,args);
                return;
            }
            case "INFO": {
                logInfo(msg,args);
                return;
            }
            case "TRACE": {
                logTrace(msg,args);
                return;
            }
            case "WARN": {
                logWarn(msg,args);
                return;
            }
            default: {
                Logger logger = logger();
                if(Objects.nonNull(logger)) logger.log(level,msg,args);
            }
        }
    }
    
    void logDebug(String msg, Object ... args);
    void logError(String msg, Object ... args);
    void logFatal(String msg, Object ... args);
    void logInfo(String msg, Object ... args);
    void logTrace(String msg, Object ... args);
    void logWarn(String msg, Object ... args);
    Logger logger();
}