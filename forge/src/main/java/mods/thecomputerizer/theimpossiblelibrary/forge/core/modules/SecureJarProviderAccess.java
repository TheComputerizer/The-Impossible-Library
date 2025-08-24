package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.AbstractModuleSystemAccessor;

import java.util.List;

/**
 * cpw.mods.jarhandling.SecureJar#Provider
 */
public class SecureJarProviderAccess extends AbstractModuleSystemAccessor {
    
    SecureJarProviderAccess(Object access, Object accessorOrLogger) {
        super(access,accessorOrLogger);
    }
    
    public List<String> providers() {
        return invoke("providers");
    }
    
    public String serviceName() {
        return invoke("serviceName");
    }
}