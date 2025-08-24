package mods.thecomputerizer.theimpossiblelibrary.api.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * java.lang.module.ModuleDescriptor#Builder
 */
public class ModuleDescriptorBuilderAccess extends AbstractModuleSystemAccessor {
    
    ModuleDescriptorBuilderAccess(Object builder, Object accessorOrLogger) {
        super(builder,accessorOrLogger);
    }
    
    @IndirectCallers
    public ModuleDescriptorAccess build() {
        return getModuleDescriptor(invoke("build"));
    }
    
    @IndirectCallers
    public void setPackages(Set<String> packages) {
        invoke("packages",packages);
    }
    
    @IndirectCallers
    public void setProvides(String serviceName, List<String> providers) {
        invoke("provides",serviceName,providers);
    }
    
    @IndirectCallers
    public void setUses(Collection<String> services) {
        for(String serviceName : services) setUses(serviceName);
    }
    
    public void setUses(String serviceName) {
        invoke("uses",serviceName);
    }
    
    @IndirectCallers
    public void setVersion(String version) {
        invoke("version",version);
    }
}