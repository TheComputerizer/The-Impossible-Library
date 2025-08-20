package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import java.util.Set;

/**
 * java.lang.module.ModuleDescriptor
 */
public class ModuleDescriptorAccess extends AbstractModuleSystemAccessor {
    
    ModuleDescriptorAccess(Object moduleDescriptor, Object accessorOrLogger) {
        super(moduleDescriptor,accessorOrLogger);
    }
    
    public String name() {
        return invokeDirect("name");
    }
    
    public Set<String> packages() {
        return asSet(invokeDirect("packages"));
    }
    
    public void setName(String name) {
        setDirect("name",name);
    }
}