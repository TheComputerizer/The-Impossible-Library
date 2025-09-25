package mods.thecomputerizer.theimpossiblelibrary.api.io;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

public interface TILLoggable extends LoggableAPI {
    
    @Override default void log(Level level, String msg, Object ... args) {
        TILRef.log(level,msg,args);
    }
    
    @Override default void logDebug(String msg, Object ... args) {
        TILRef.logDebug(msg,args);
    }
    
    @Override default void logError(String msg, Object ... args) {
        TILRef.logError(msg,args);
    }
    
    @Override default void logFatal(String msg, Object ... args) {
        TILRef.logFatal(msg,args);
    }
    
    @Override default void logInfo(String msg, Object ... args) {
        TILRef.logInfo(msg,args);
    }
    
    @Override default void logTrace(String msg, Object ... args) {
        TILRef.logTrace(msg,args);
    }
    
    @Override default void logWarn(String msg, Object ... args) {
        TILRef.logWarn(msg,args);
    }
    
    /**
     * We don't need to return the default library logger
     */
    @Override default Logger logger() {
        return null;
    }
}