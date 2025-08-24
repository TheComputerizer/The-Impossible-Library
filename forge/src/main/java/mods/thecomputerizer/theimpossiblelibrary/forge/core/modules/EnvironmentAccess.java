package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.AbstractModuleSystemAccessor;

/**
 * cpw.mods.modlauncher.Environment
 * implements cpw.mods.modlauncher.api.IEnvironment
 */
public class EnvironmentAccess extends AbstractModuleSystemAccessor {
    
    EnvironmentAccess(Object access, Object accessorOrLogger) {
        super(access,accessorOrLogger);
    }
    
    public ModuleLayerHandlerAccess getModuleLayerHandler() {
        Object handler = asOptionalResult(invoke("findModuleLayerManager"));
        return ForgeModuleAccess.getModuleLayerHandler(handler,this);
    }
}
