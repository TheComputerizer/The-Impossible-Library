package mods.thecomputerizer.theimpossiblelibrary.api.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * jdk.internal.module.ServicesCatalog
 */
public class ServicesCatalogAccess extends AbstractModuleSystemAccessor {
    
    static final String SERVICE_PROVIDER_CLASS = "jdk.internal.module.ServicesCatalog$ServiceProvider";
    
    ServicesCatalogAccess(Object servicesCatalog, Object accessorOrLogger) {
        super(servicesCatalog,accessorOrLogger);
    }
    
    @IndirectCallers
    public void addProvider(String serviceName, ModuleAccess module, String providerName) {
        addProvider(serviceName,constructProvider(module,providerName));
    }
    
    public void addProvider(String serviceName, Object module, String providerName) {
        addProvider(serviceName,constructProvider(module,providerName));
    }
    
    public void addProvider(String serviceName, ServiceProviderAccess provider) {
        invokeDirect("addProviders",serviceName,provider.access);
    }
    
    public ServiceProviderAccess asProvider(Object provider) {
        return new ServiceProviderAccess(provider,this);
    }
    
    public ServiceProviderAccess constructProvider(ModuleAccess module, String providerName) {
        return constructProvider(module.access(),providerName);
    }
    
    public ServiceProviderAccess constructProvider(Object module, String providerName) {
        return asProvider(Hacks.construct(providerClass(),module,providerName));
    }
    
    public Class<?> providerClass() {
        return Hacks.findClass(SERVICE_PROVIDER_CLASS);
    }
    
    public Map<String,List<Object>> providers() {
        return getDirect("map");
    }
    
    @SuppressWarnings("UnusedReturnValue")
    public boolean removeImplementations(String service, String impl) {
        return providers().getOrDefault(service,new ArrayList<>())
                .removeIf(provider -> impl.equals(asProvider(provider).providerName()));
    }
}
