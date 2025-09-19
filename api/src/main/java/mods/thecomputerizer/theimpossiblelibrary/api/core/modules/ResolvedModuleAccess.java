package mods.thecomputerizer.theimpossiblelibrary.api.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * java.lang.module.ResolvedModule
 */
public class ResolvedModuleAccess extends AbstractModuleSystemAccessor {
    
    ResolvedModuleAccess(Object resolvedModule, Object accessorOrLogger) {
        super(resolvedModule,accessorOrLogger);
    }
    
    @IndirectCallers
    public ConfigurationAccess configuration() {
        return getConfiguration(invoke("configuration"));
    }
    
    public String name() {
        return descriptor().name();
    }
    
    public ModuleDescriptorAccess descriptor() {
        return getModuleDescriptor(invokeDirect("descriptor"));
    }
    
    public Set<String> filteredPackages(Collection<String> allPackages) {
        Set<String> packages = packages(true);
        packages.removeAll(allPackages);
        allPackages.addAll(packages);
        return packages;
    }
    
    public void inheritFrom(ConfigurationAccess configuration, String ... moduleNames) {
        for(String moduleName : moduleNames) inheritFrom(configuration.getModule(moduleName));
    }
    
    public void inheritFrom(ResolvedModuleAccess resolvedModule) {
        if(Objects.nonNull(resolvedModule)) descriptor().inheritFrom(resolvedModule.descriptor());
    }
    
    public Set<String> packages() {
        return packages(false);
    }
    
    @IndirectCallers
    public Set<String> packages(boolean modifiable) {
        Set<String> packages = descriptor().packages();
        return modifiable ? new HashSet<>(packages) : packages;
    }
    
    @IndirectCallers
    public ModuleReferenceAccess reference() {
        return getModuleReference(invokeDirect("reference"));
    }
    
    public void setConfiguration(ConfigurationAccess configuration) {
        setConfiguration(configuration.access);
    }
    
    public void setConfiguration(Object configuration) {
        setDirect("cf",configuration);
    }
    
    public void setName(String name) {
        descriptor().setName(name);
    }
    
    @Override public String toString() {
        return "ResolvedModuleAccess["+name()+"]";
    }
}