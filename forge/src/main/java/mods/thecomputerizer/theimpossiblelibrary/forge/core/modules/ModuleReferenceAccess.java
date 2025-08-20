package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.net.URI;

/**
 * java.lang.module.ModuleReference
 */
public class ModuleReferenceAccess extends AbstractModuleSystemAccessor {
    
    ModuleReferenceAccess(Object moduleReference, Object accessorOrLogger) {
        super(moduleReference,accessorOrLogger);
    }
    
    @IndirectCallers
    public ModuleDescriptorAccess descriptor() {
        return getModuleDescriptor(get("descriptor"));
    }
    
    @IndirectCallers
    public URI location() {
        return getDirect("location");
    }
    
    public String name() {
        return descriptor().name();
    }
    
    @IndirectCallers
    public ResolvedModuleAccess newResolvedModule(Object configuration) {
        return newResolvedModule(configuration,this.access);
    }
    
    public void setDescriptor(ModuleDescriptorAccess moduleDescriptor) {
        setDescriptor(moduleDescriptor.access);
    }
    
    public void setDescriptor(Object moduleDescriptor) {
        set("descriptor",moduleDescriptor);
    }
}