package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader;

import cpw.mods.jarhandling.LazyJarMetadata;
import cpw.mods.jarhandling.SecureJar.Provider;

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleDescriptor.Builder;
import java.util.List;

import static java.lang.System.out;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;

/**
 * Override JarMetadata for the loader so there aren't any duplicate module definitions
 */
public class TILLoaderJarMetadata extends LazyJarMetadata {
    
    static TILLoaderJarMetadata get(String moduleName) {
        return new TILLoaderJarMetadata(moduleName);
    }
    
    final String moduleName;
    
    TILLoaderJarMetadata(String moduleName) {
        this.moduleName = moduleName;
    }
    
    ModuleDescriptor buildNewModule() {
        String moduleName = name();
        out.println("Building new ModuleDescriptor for "+moduleName);
        Builder builder = ModuleDescriptor.newAutomaticModule(moduleName);
        builder.version(version());
        return builder.build();
    }
    
    @Override protected ModuleDescriptor computeDescriptor() {
        return buildNewModule();
    }
    
    @Override public String name() {
        return this.moduleName;
    }
    
    @Override public List<Provider> providers() {
        return List.of();
    }
    
    @Override public String version() {
        return VERSION;
    }
}