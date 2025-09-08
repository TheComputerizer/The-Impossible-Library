package mods.thecomputerizer.theimpossiblelibrary.api.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
    
    boolean containsProviderFor(String service, String impl, String moduleName) {
        Map<String,List<Object>> existingProviders = providers();
        if(!existingProviders.containsKey(service)) return false;
        for(Object provider : existingProviders.get(service)) {
            ServiceProviderAccess access = asProvider(provider);
            if(impl.equals(access.providerName()) && moduleName.equals(access.moduleName())) return true;
        }
        return false;
    }
    
    Map<String,List<String>> getProvidersFor(String module) {
        Map<String,List<String>> providers = new HashMap<>();
        for(Entry<String,List<Object>> serviceEntry : providers().entrySet()) {
            String service = serviceEntry.getKey();
            for(Object provider : serviceEntry.getValue()) {
                ServiceProviderAccess access = asProvider(provider);
                if(module.equals(access.moduleName())) {
                    providers.putIfAbsent(service,new ArrayList<>());
                    providers.get(service).add(access.providerName());
                }
            }
        }
        return providers;
    }
    
    public void inheritProviders(ServicesCatalogAccess catalog, Object module, String moduleName) {
        Map<String,List<String>> serviceMap = catalog.getProvidersFor(moduleName);
        for(Entry<String,List<String>> serviceEntry : serviceMap.entrySet()) {
            String service = serviceEntry.getKey();
            for(String provider : serviceEntry.getValue())
                if(!containsProviderFor(service,provider,moduleName)) addProvider(service,module,provider);
        }
        catalog.removeProviders(serviceMap);
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
    
    void removeProviders(Map<String,List<String>> serviceMap) {
        Map<String,List<Object>> existingServiceMap = providers();
        for(Entry<String,List<String>> serviceEntry : serviceMap.entrySet()) {
            String service = serviceEntry.getKey();
            List<String> providers = serviceEntry.getValue();
            if(existingServiceMap.containsKey(service)) {
                List<Object> values = existingServiceMap.get(service);
                values.removeIf(o -> providers.contains(asProvider(o).providerName()));
                if(values.isEmpty()) existingServiceMap.remove(service);
            }
        }
    }
}
