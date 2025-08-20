package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import java.util.List;

/**
 * net.minecraftforge.fml.loading.moddiscovery.ModFileInfo
 */
public class ModFileInfoAccess extends AbstractModuleSystemAccessor {
    
    ModFileInfoAccess(Object access, Object accessorOrLogger) {
        super(access,accessorOrLogger);
    }
    
    public ModFileAccess file() {
        return getModFile(invoke("getFile"));
    }
    
    public String moduleName() {
        return invoke("moduleName");
    }
    
    public ModuleDescriptorAccess newModuleDescriptor(String moduleName, SecureJarAccess secureJar) {
        return secureJar.newModuleDescriptor(moduleName,usesServices());
    }
    
    public SecureJarAccess secureJar() {
        return file().secureJar();
    }
    
    public List<String> usesServices() {
        return invoke("usesServices");
    }
}
