package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.AbstractModuleSystemAccessor;

/**
 * cpw.mods.jarhandling.JarMetadata
 */
public class JarMetadataAccess extends AbstractModuleSystemAccessor {
    
    JarMetadataAccess(Object jarMetadata, Object accessorOrLogger) {
        super(jarMetadata,accessorOrLogger);
    }
    
    String version() {
        return invoke("version");
    }
}