package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

/**
 * jdk.internal.module.ServicesCatalog#ServiceProvider
 */
public class ServiceProviderAccess extends AbstractModuleSystemAccessor {
    
    ServiceProviderAccess(Object access, Object accessorOrLogger) {
        super(access,accessorOrLogger);
    }
    
    public ModuleAccess module() {
        return getModule(invokeDirect("module"));
    }
    
    public String moduleName() {
        return module().getName();
    }
    
    public String providerName() {
        return invokeDirect("providerName");
    }
}
