package mods.thecomputerizer.theimpossiblelibrary.api.core.modules;

/**
 * jdk.internal.access.JavaLangAccess
 */
public class JavaLangAccess extends AbstractModuleSystemAccessor {
    
    JavaLangAccess(Object access, Object accessorOrLogger) {
        super(access,accessorOrLogger);
    }
    
    public ServicesCatalogAccess getServicesCatalog(ModuleLayerAccess moduleLayer) {
        return getServicesCatalog(moduleLayer.access);
    }
    
    public ServicesCatalogAccess getServicesCatalog(Object moduleLayer) {
        return new ServicesCatalogAccess(invokeDirect("getServicesCatalog",moduleLayer),this);
    }
}