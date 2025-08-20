package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

/**
 * net.minecraftforge.fml.loading.moddiscovery.ModFile
 */
public class ModFileAccess extends AbstractModuleSystemAccessor {
    
    ModFileAccess(Object access, Object accessorOrLogger) {
        super(access,accessorOrLogger);
    }
    
    @IndirectCallers
    public ModFileInfoAccess fileInfo() {
        return invoke("getModFileInfo");
    }
    
    @IndirectCallers
    public SecureJarAccess secureJar() {
        return getSecureJar(invoke("getSecureJar"));
    }
}
