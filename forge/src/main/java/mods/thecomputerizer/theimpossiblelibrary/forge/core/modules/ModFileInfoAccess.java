package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.AbstractModuleSystemAccessor;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleDescriptorAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleLayerAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleReferenceAccess;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * net.minecraftforge.fml.loading.moddiscovery.ModFileInfo
 */
public class ModFileInfoAccess extends AbstractModuleSystemAccessor {
    
    ModFileInfoAccess(Object access, Object accessorOrLogger) {
        super(access,accessorOrLogger);
    }
    
    public ModFileAccess file() {
        return ForgeModuleAccess.getModFile(invoke("getFile"),this);
    }
    
    public ModuleReferenceHolder getJarModule(ModuleClassLoaderAccess loader, String modid,
            Consumer<String> jarNameMismatchHandler) {
        ModuleLayerAccess moduleLayer = loader.getModuleLayer();
        ModuleAccess module = moduleLayer.getAnyModule(modid, jarName());
        ModuleDescriptorAccess descriptor;
        if(Objects.nonNull(module)) {
            logOrPrint("Found existing module to set up for "+modid, Logger::info);
            descriptor = module.getDescriptor();
            descriptor.setName(modid);
            String jarName = secureJar().name();
            if(!modid.equals(jarName)) jarNameMismatchHandler.accept(jarName);
            return ModuleReferenceHolder.of(module,newModuleFinderReference(descriptor));
        }
        logOrPrint("Setting up new module with name "+modid, Logger::info);
        descriptor = newModuleDescriptor(modid,secureJar());
        ModuleReferenceAccess reference = newModuleFinderReference(descriptor);
        return ModuleReferenceHolder.of(moduleLayer.newModule(loader,descriptor,reference.location()),reference);
    }
    
    public String jarName() {
        return secureJar().name();
    }
    
    public String moduleName() {
        return invoke("moduleName");
    }
    
    public ModuleDescriptorAccess newModuleDescriptor(String moduleName, SecureJarAccess secureJar) {
        return secureJar.newModuleDescriptor(moduleName,usesServices());
    }
    
    public ModuleReferenceAccess newModuleFinderReference(ModuleDescriptorAccess moduleDescriptor) {
        SecureJarAccess secureJar = secureJar();
        ModuleFinderAccess moduleFinder = secureJar.newModuleFinder();
        return moduleFinder.getModuleReference(secureJar.name(),moduleDescriptor);
    }
    
    public SecureJarAccess secureJar() {
        return file().secureJar();
    }
    
    public List<String> usesServices() {
        return invoke("usesServices");
    }
}
