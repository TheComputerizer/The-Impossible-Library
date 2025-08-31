package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.AbstractModuleSystemAccessor;

/**
 * cpw.mods.modlauncher.ArgumentHandler
 */
public class ArgumentHandlerAccess extends AbstractModuleSystemAccessor {
    
    ArgumentHandlerAccess(Object handler, Object accessorOrLogger) {
        super(handler,accessorOrLogger);
    }
    
    public String[] getArgs() {
        return getDirect("args");
    }
}