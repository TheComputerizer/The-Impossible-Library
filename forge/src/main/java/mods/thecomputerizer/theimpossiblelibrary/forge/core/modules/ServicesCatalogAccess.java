package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * jdk.internal.module.ServicesCatalog
 */
public class ServicesCatalogAccess extends AbstractModuleSystemAccessor {
    
    ServicesCatalogAccess(Object access, Object accessorOrLogger) {
        super(access,accessorOrLogger);
    }
    
    public ServiceProviderAccess asProvider(Object provider) {
        return new ServiceProviderAccess(provider,this);
    }
    
    public Map<String,List<Object>> providers() {
        return getDirect("map");
    }
    
    public void removeImplementations(String service, String impl) {
        providers().getOrDefault(service,new ArrayList<>())
                .removeIf(provider -> impl.equals(asProvider(provider).providerName()));
    }
}
