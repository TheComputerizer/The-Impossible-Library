package mods.thecomputerizer.theimpossiblelibrary.api.io;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

public interface TILLoggable extends LoggableAPI {
    
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
}