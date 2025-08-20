package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.HashSet;
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
    
    public void setConfiguration(ModuleClassLoaderAccess moduleClassLoader) {
        setConfiguration(moduleClassLoader.configuration());
    }
    
    public void setConfiguration(ConfigurationAccess configuration) {
        setConfiguration(configuration.access);
    }
    
    public void setConfiguration(Object configuration) {
        setDirect("cf",configuration);
    }
}