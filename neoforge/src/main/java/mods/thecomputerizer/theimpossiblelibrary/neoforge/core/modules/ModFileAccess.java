package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.AbstractModuleSystemAccessor;

/**
 * net.minecraftforge.fml.loading.moddiscovery.ModFile
 */
public class ModFileAccess extends AbstractModuleSystemAccessor {
    
    ModFileAccess(Object modFile, Object accessorOrLogger) {
        super(modFile,accessorOrLogger);
    }
    
    @IndirectCallers
    public ModFileInfoAccess fileInfo() {
        return invoke("getModFileInfo");
    }
    
    @IndirectCallers
    public SecureJarAccess secureJar() {
        return NeoforgeModuleAccess.getSecureJar(invoke("getSecureJar"),this);
    }
}