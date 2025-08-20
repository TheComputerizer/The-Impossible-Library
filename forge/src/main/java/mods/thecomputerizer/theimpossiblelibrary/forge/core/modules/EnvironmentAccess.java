package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

/**
 * cpw.mods.modlauncher.Environment
 * implements cpw.mods.modlauncher.api.IEnvironment
 */
public class EnvironmentAccess extends AbstractModuleSystemAccessor {
    
    EnvironmentAccess(Object access, Object accessorOrLogger) {
        super(access,accessorOrLogger);
    }
    
    @Override
    public ModuleLayerHandlerAccess getModuleLayerHandler() {
        Object handler = asOptionalResult(invoke("findModuleLayerManager"));
        return getModuleLayerHandler(handler);
    }
}
