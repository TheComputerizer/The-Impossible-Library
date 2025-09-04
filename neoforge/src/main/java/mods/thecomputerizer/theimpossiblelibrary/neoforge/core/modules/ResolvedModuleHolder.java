package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules;

import cpw.mods.modlauncher.api.IModuleLayerManager.Layer;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ResolvedModuleAccess;

import java.util.Objects;
import java.util.Set;

/**
 Simple holder class for a ResolvedModule and the ModuleClassLoader it was found in
 */
public record ResolvedModuleHolder(ResolvedModuleAccess module, ModuleClassLoaderAccess loader) {
    
    public static ResolvedModuleHolder findModule(String moduleName, ModuleClassLoaderAccess ... loaders) {
        for(ModuleClassLoaderAccess loader : loaders) {
            ResolvedModuleHolder holder = findModule(moduleName, loader);
            if(Objects.nonNull(holder)) return holder;
        }
        return null;
    }
    
    public static ResolvedModuleHolder findModule(String moduleName, ModuleClassLoaderAccess loader) {
        ResolvedModuleAccess resolvedModule = loader.configuration().getModule(moduleName);
        return Objects.nonNull(resolvedModule) ? new ResolvedModuleHolder(resolvedModule, loader) : null;
    }
    
    public static ResolvedModuleHolder findPackage(String pkg, ModuleClassLoaderAccess ... loaders) {
        for(ModuleClassLoaderAccess loader : loaders) {
            ResolvedModuleHolder holder = findPackage(pkg, loader);
            if(Objects.nonNull(holder)) return holder;
        }
        return null;
    }
    
    @IndirectCallers
    public static ResolvedModuleHolder findPackage(String pkg, Layer ... layers) {
        for(Layer layer : layers) {
            ResolvedModuleHolder holder = findPackage(pkg,NeoforgeModuleAccess.getModuleClassLoader(layer));
            if(Objects.nonNull(holder)) return holder;
        }
        return null;
    }
    
    public static ResolvedModuleHolder findPackage(String pkg, ModuleClassLoaderAccess loader) {
        ResolvedModuleAccess resolvedModule = loader.getResolvedModule(pkg);
        return Objects.nonNull(resolvedModule) ? new ResolvedModuleHolder(resolvedModule, loader) : null;
    }
    
    /**
     Assumes the input ModuleClassLoaderAccess has its layerName field set
     */
    public ResolvedModuleHolder {
    }
    
    @IndirectCallers
    public Layer getLayer() {
        return this.loader.layer;
    }
    
    @IndirectCallers
    public Set<String> getPackages() {
        return this.module.packages();
    }
    
    public String layerName() {
        return getLayer().name();
    }
    
    public String moduleName() {
        return this.module.name();
    }
    
    /**
     * Get a resolved module in the current loader.
     * Returns null if it isn't found or this if the package is in the same module.
     */
    @IndirectCallers
    public ResolvedModuleHolder otherPackageRef(String pkg) {
        ResolvedModuleHolder holder = findPackage(pkg,this.loader);
        if(Objects.nonNull(holder) && moduleName().equals(holder.moduleName())) return this;
        return holder;
    }
    
    public void removePackagesFromLoader() {
        this.loader.removePackagesForModule(this.module);
    }
}