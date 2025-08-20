package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

/**
 * cpw.mods.modlauncher.ArgumentHandler
 */
public class ArgumentHandlerAccess extends AbstractModuleSystemAccessor {
    
    ArgumentHandlerAccess(Object access, Object accessorOrLogger) {
        super(access,accessorOrLogger);
    }
    
    public String[] getArgs() {
        return getDirect("args");
    }
}
