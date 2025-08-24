package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.AbstractModuleSystemAccessor;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleDescriptorAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleReferenceAccess;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Objects;

/**
 * java.lang.module.ModuleFinder
 */
public class ModuleFinderAccess extends AbstractModuleSystemAccessor {
    
    static final String moduleReferenceMapField = ForgeModuleAccess.changing("references","moduleReferenceMap");
    
    ModuleFinderAccess(Object access, Object accessorOrLogger) {
        super(access,accessorOrLogger);
    }
    
    public ModuleReferenceAccess getModuleReference(String name) {
        Object moduleReference = moduleReferenceMap().get(name);
        if(Objects.nonNull(moduleReference)) return getModuleReference(moduleReference);
        logOrPrint("Module "+name+" not found in moduleReferenceMap of ModuleFinder",Logger::debug);
        return null;
    }
    
    public ModuleReferenceAccess getModuleReference(String name, ModuleDescriptorAccess moduleDescriptor) {
        ModuleReferenceAccess moduleReference = getModuleReference(name);
        moduleReference.setDescriptor(moduleDescriptor);
        return moduleReference;
    }
    
    public Map<String,Object> moduleReferenceMap() {
        return getDirect(moduleReferenceMapField);
    }
}
