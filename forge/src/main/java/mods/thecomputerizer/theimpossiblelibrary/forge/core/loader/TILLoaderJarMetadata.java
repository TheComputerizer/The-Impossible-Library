package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;

import java.util.Collections;
import java.util.Objects;

import static java.lang.System.out;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.VERSION;

/**
 * Override JarMetadata for the loader so there aren't any duplicate module definitions
 */
public class TILLoaderJarMetadata {
    
    static final String JAR_METADATA = "cpw.mods.jarhandling.JarMetadata";
    static final String MODULE_DESCRIPTOR = "java.lang.module.ModuleDescriptor";
    
    static Object buildNewModule(final String moduleName, final String version) {
        out.println("Building new ModuleDescriptor for "+moduleName);
        Object builder = Hacks.invokeStatic(MODULE_DESCRIPTOR,"newAutomaticModule",moduleName);
        Hacks.invoke(builder,"version",version);
        return Hacks.invoke(builder,"build");
    }
    
    static Object get(String moduleName) {
        return new TILLoaderJarMetadata(moduleName,VERSION).metadataProxy;
    }
    
    final Object metadataProxy;
    Object descriptor;
    
    TILLoaderJarMetadata(final String moduleName, final String version) {
        this.metadataProxy = createProxy(getClass().getClassLoader(),moduleName,version);
    }
    
    Object createProxy(final ClassLoader loader, final String moduleName, final String version) {
        return ClassHelper.newGenericProxy(loader,JAR_METADATA,(methodName,args) -> {
            switch(methodName) {
                case "descriptor": {
                    if(Objects.isNull(this.descriptor)) this.descriptor = buildNewModule(moduleName,version);
                    return this.descriptor;
                }
                case "name": return moduleName;
                case "providers": return Collections.emptyList();
                case "version": return version;
                default: return null;
            }
        });
    }
}