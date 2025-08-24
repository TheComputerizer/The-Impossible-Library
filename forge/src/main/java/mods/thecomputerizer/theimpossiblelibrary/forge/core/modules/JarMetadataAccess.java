package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.AbstractModuleSystemAccessor;

/**
 * cpw.mods.jarhandling.JarMetadata
 */
public class JarMetadataAccess extends AbstractModuleSystemAccessor {
    
    JarMetadataAccess(Object access, Object accessorOrLogger) {
        super(access,accessorOrLogger);
    }
    
    String version() {
        return invoke("version");
    }
}