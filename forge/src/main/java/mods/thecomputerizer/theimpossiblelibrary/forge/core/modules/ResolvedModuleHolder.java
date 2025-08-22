package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.Objects;
import java.util.Set;

/**
 * Simple holder class for a ResolvedModule and the ModuleClassLoader it was found in
 */
@Getter
public class ResolvedModuleHolder {
    
    public static ResolvedModuleHolder findPackage(String pkg, ModuleClassLoaderAccess ... loaders) {
        for(ModuleClassLoaderAccess loader : loaders) {
            ResolvedModuleHolder holder = findPackage(pkg,loader);
            if(Objects.nonNull(holder)) return holder;
        }
        return null;
    }
    
    @IndirectCallers
    public static ResolvedModuleHolder findPackage(String pkg, String ... layerNames) {
        for(String layerName : layerNames) {
            ResolvedModuleHolder holder = findPackage(pkg,ModuleSystemAccessor.getModuleClassLoader(layerName));
            if(Objects.nonNull(holder)) return holder;
        }
        return null;
    }
    
    public static ResolvedModuleHolder findPackage(String pkg, ModuleClassLoaderAccess loader) {
        ResolvedModuleAccess resolvedModule = loader.getResolvedModule(pkg);
        return Objects.nonNull(resolvedModule) ? new ResolvedModuleHolder(resolvedModule,loader) : null;
    }
    
    final ResolvedModuleAccess module;
    final ModuleClassLoaderAccess loader;
    
    /**
     * Assumes the input ModuleClassLoaderAccess has its layerName field set
     */
    ResolvedModuleHolder(ResolvedModuleAccess resolvedModule, ModuleClassLoaderAccess loader) {
        this.module = resolvedModule;
        this.loader = loader;
    }
    
    @IndirectCallers
    public String getLayerName() {
        return this.loader.layerName;
    }
    
    @IndirectCallers
    public Set<String> getPackages() {
        return this.module.packages();
    }
}