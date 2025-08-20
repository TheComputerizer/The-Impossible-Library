package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

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