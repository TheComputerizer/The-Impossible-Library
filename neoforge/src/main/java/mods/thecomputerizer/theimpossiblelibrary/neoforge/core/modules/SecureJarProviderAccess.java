package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules;

import cpw.mods.jarhandling.SecureJar.Provider;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.AbstractModuleSystemAccessor;

import java.util.List;

/**
 * cpw.mods.jarhandling.SecureJar#Provider
 */
public class SecureJarProviderAccess extends AbstractModuleSystemAccessor {
    
    SecureJarProviderAccess(Object jarProvider, Object accessorOrLogger) {
        super(jarProvider,accessorOrLogger);
    }
    
    public List<String> providers() {
        return ((Provider)accessAs()).providers();
    }
    
    public String serviceName() {
        return ((Provider)accessAs()).serviceName();
    }
}