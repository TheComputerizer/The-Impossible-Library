package mods.thecomputerizer.theimpossiblelibrary.api.core.modules;

/**
 * jdk.internal.module.ServicesCatalog#ServiceProvider
 */
public class ServiceProviderAccess extends AbstractModuleSystemAccessor {
    
    ServiceProviderAccess(Object serviceProvider, Object accessorOrLogger) {
        super(serviceProvider,accessorOrLogger);
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
