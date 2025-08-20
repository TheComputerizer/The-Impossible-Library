package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Objects;

/**
 * java.lang.module.ModuleFinder
 */
public class ModuleFinderAccess extends AbstractModuleSystemAccessor {
    
    static final String moduleReferenceMapField = changing("references","moduleReferenceMap");
    
    ModuleFinderAccess(Object access, Object accessorOrLogger) {
        super(access,accessorOrLogger);
    }
    
    @IndirectCallers
    public ModuleReferenceAccess getModuleReference(String name) {
        Object moduleReference = moduleReferenceMap().get(name);
        if(Objects.nonNull(moduleReference)) return getModuleReference(moduleReference);
        logOrPrint("Module "+name+" not found in moduleReferenceMap of ModuleFinder",Logger::debug);
        return null;
    }
    
    public Map<String,Object> moduleReferenceMap() {
        return getDirect(moduleReferenceMapField);
    }
}
