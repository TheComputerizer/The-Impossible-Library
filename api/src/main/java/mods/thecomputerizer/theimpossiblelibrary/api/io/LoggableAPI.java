package mods.thecomputerizer.theimpossiblelibrary.api.io;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

public interface LoggableAPI {

    @IndirectCallers void logDebug(String msg, Object ... args);
    @IndirectCallers void logError(String msg, Object ... args);
    @IndirectCallers void logFatal(String msg, Object ... args);
    @IndirectCallers void logInfo(String msg, Object ... args);
    @IndirectCallers void logTrace(String msg, Object ... args);
    @IndirectCallers void logWarn(String msg, Object ... args);
}