package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader.SECURE_CLASSLOADER_FORMAT;

/**
 * cpw.mods.jarhandling.impl.Jar
 * implements cpw.mods.jarhandling.SecureJar
 * It's generally safe to assume that every SecureJar implementation will be a Jar instance
 */
public class SecureJarAccess extends AbstractModuleSystemAccessor {
    
    SecureJarAccess(Object access, Object accessorOrLogger) {
        super(access,accessorOrLogger);
    }
    
    @IndirectCallers
    public JarMetadataAccess metadata() {
        return getJarMetadata(get("metadata"));
    }
    
    public String name() {
        return invoke("name");
    }
    
    public Set<String> packages() {
        return invoke("getPackages");
    }
    
    @IndirectCallers
    public List<Object> providers() {
        return invoke("getProviders");
    }
    
    public ModuleDescriptorAccess newModuleDescriptor(String moduleName, List<String> usesServices) {
        ModuleDescriptorBuilderAccess builder = getModuleDescriptorBuilder(moduleName);
        builder.inheritFromSecureJar(this);
        builder.setUses(usesServices);
        return builder.build();
    }
    
    public ModuleFinderAccess newModuleFinder() {
        Object arg = SECURE_CLASSLOADER_FORMAT ? Collections.singletonList(this.access) : this.access;
        return construct(MODULE_FINDER_EXTENSION_CLASS,arg);
    }
}