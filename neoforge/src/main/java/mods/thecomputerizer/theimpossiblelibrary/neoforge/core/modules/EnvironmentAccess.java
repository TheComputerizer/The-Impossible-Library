package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.AbstractModuleSystemAccessor;

/**
 * cpw.mods.modlauncher.Environment
 * implements cpw.mods.modlauncher.api.IEnvironment
 */
public class EnvironmentAccess extends AbstractModuleSystemAccessor {
    
    EnvironmentAccess(Object environment, Object accessorOrLogger) {
        super(environment,accessorOrLogger);
    }
    
    public ModuleLayerHandlerAccess getModuleLayerHandler() {
        Object handler = asOptionalResult(invoke("findModuleLayerManager"));
        return NeoforgeModuleAccess.getModuleLayerHandler(handler,this);
    }
}