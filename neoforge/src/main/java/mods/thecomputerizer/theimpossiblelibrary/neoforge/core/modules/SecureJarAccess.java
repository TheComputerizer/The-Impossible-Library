package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules;

import cpw.mods.cl.JarModuleFinder;
import cpw.mods.jarhandling.SecureJar;
import cpw.mods.jarhandling.SecureJar.Provider;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.AbstractModuleSystemAccessor;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleDescriptorAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleDescriptorBuilderAccess;

import java.net.URI;
import java.util.List;
import java.util.Set;

/**
 * cpw.mods.jarhandling.impl.Jar
 * implements cpw.mods.jarhandling.SecureJar
 * It's generally safe to assume that every SecureJar implementation will be a Jar instance
 */
public class SecureJarAccess extends AbstractModuleSystemAccessor {
    
    SecureJarAccess(Object secureJar, Object accessorOrLogger) {
        super(secureJar,accessorOrLogger);
    }
    
    public JarMetadataAccess metadata() {
        return NeoforgeModuleAccess.getJarMetadata(get("metadata"),this);
    }
    
    public String name() {
        return invoke("name");
    }
    
    public ModuleDescriptorAccess newModuleDescriptor(String moduleName, List<String> usesServices) {
        ModuleDescriptorBuilderAccess builder = getModuleDescriptorBuilder(moduleName);
        builder.setVersion(metadata().version());
        builder.setPackages(packages());
        for(Provider jarProvider : providers()) {
            SecureJarProviderAccess providerAccess = NeoforgeModuleAccess.getSecureJarProvider(jarProvider);
            List<String> providers = providerAccess.providers();
            if(!providers.isEmpty()) builder.setProvides(providerAccess.serviceName(),providers);
        }
        builder.setUses(usesServices);
        return builder.build();
    }
    
    public ModuleFinderAccess newModuleFinder() {
        return construct(JarModuleFinder.class,(Object)new SecureJar[]{accessAs()});
    }
    
    public Set<String> packages() {
        return invoke("getPackages");
    }
    
    /**
     * Returns a string consisting of the name and URI for logging
     */
    @IndirectCallers
    public String print() {
        return "name = '"+name()+"' | location = '"+uri()+"'";
    }
    
    public List<Provider> providers() {
        return invoke("getProviders");
    }
    
    public URI uri() {
        return ((SecureJar)accessAs()).moduleDataProvider().uri();
    }
}