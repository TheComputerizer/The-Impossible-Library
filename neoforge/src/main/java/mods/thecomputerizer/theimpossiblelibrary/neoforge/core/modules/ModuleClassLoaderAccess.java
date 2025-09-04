package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules;

import cpw.mods.cl.ModuleClassLoader;
import cpw.mods.modlauncher.api.IModuleLayerManager.Layer;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ClassLoaderAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ConfigurationAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleDescriptorAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleHolder;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleLayerAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleReferenceAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleSystemAccessor;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ResolvedModuleAccess;

import java.lang.module.ModuleReference;
import java.lang.module.ResolvedModule;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.BOOT;

/**
 * [1.18.2+] cpw.mods.cl.ModuleClassLoader
 * [1.20.4+] extends net.minecraftforge.securemodules.SecureModuleClassLoader
 */
public class ModuleClassLoaderAccess extends ClassLoaderAccess implements ModuleHolder {
    
    @Setter Layer layer;
    
    ModuleClassLoaderAccess(ModuleClassLoader loader, Object accessorOrLogger) {
        super(loader,accessorOrLogger);
    }
    
    public void addPackages(ResolvedModuleAccess resolvedModule) {
        addPackages(resolvedModule.reference().descriptor(),resolvedModule);
    }
    
    public void addPackages(ModuleDescriptorAccess moduleDescriptor, ResolvedModuleAccess resolvedModule) {
        addPackages(moduleDescriptor.packages(),resolvedModule);
    }
    
    public void addPackages(Collection<String> pkgs, ResolvedModuleAccess resolvedModule) {
        addPackages(pkgs,(ResolvedModule)resolvedModule.accessAs());
    }
    
    void addPackages(Collection<String> pkgs, ResolvedModule resolvedModule) {
        Map<String,ResolvedModule> packageLookup = packageLookup();
        for(String pkg : pkgs) packageLookup.put(pkg,resolvedModule);
    }
    
    @IndirectCallers
    public void addPackage(String pkg, ResolvedModuleAccess resolvedModule) {
        addPackage(pkg,(ResolvedModule)resolvedModule.accessAs());
    }
    
    void addPackage(String pkg, ResolvedModule resolvedModule) {
        packageLookup().put(pkg,resolvedModule);
    }
    
    @IndirectCallers
    public void addParentLoaders(ResolvedModuleAccess resolvedModule, ModuleClassLoaderAccess loader) {
        addParentLoaders(resolvedModule.reference().descriptor(),loader);
    }
    
    public void addParentLoaders(ModuleDescriptorAccess moduleDescriptor, ModuleClassLoaderAccess loader) {
        addParentLoaders(moduleDescriptor.packages(),loader);
    }
    
    public void addParentLoaders(Collection<String> pkgs, ModuleClassLoaderAccess loader) {
        addParentLoaders(pkgs,(ClassLoader)loader.accessAs());
    }
    
    void addParentLoaders(Collection<String> pkgs, ClassLoader loader) {
        Map<String,ClassLoader> parentLoaders = parentLoaders();
        for(String pkg : pkgs) parentLoaders.put(pkg,loader);
    }
    
    @IndirectCallers
    public void addParentLoader(String pkg, ModuleClassLoaderAccess loader) {
        addParentLoader(pkg,(ClassLoader)loader.accessAs());
    }
    
    void addParentLoader(String pkg, ClassLoader loader) {
        parentLoaders().put(pkg,loader);
    }
    
    public void addRoot(ResolvedModuleAccess resolvedModule) {
        addRoot(resolvedModule.reference());
    }
    
    public void addRoot(ModuleReferenceAccess moduleReference) {
        addRoot(moduleReference.name(),(ModuleReference)moduleReference.accessAs());
    }
    
    public void addRoot(String name, ModuleReferenceAccess moduleReference) {
        addRoot(name,(ModuleReference)moduleReference.accessAs());
    }
    
    public void addRoot(String name, ModuleReference moduleReference) {
        resolvedRoots().put(name,moduleReference);
    }
    
    @Override public void cloneModule(String moduleName, String newModuleName) {
        clonePackages(moduleName,newModuleName);
        cloneModuleMap(resolvedRoots(),moduleName,newModuleName);
    }
    
    <T> void cloneModuleMap(Map<String,T> map, String moduleName, String newModuleName) {
        if(map.containsKey(moduleName)) {
            T o = map.get(moduleName);
            map.remove(moduleName);
            if(!map.containsKey(newModuleName)) {
                getModuleReference(o).setName(newModuleName);
                map.put(newModuleName,o);
            }
        }
    }
    
    void clonePackages(String moduleName, String newModuleName) {
        ResolvedModuleAccess existingModule = lookupResolvedModule(newModuleName);
        boolean existed = Objects.nonNull(existingModule);
        Set<String> packages = existed ? new HashSet<>() : null;
        Map<String,ResolvedModule> packageLookup = packageLookup();
        for(Entry<String,ResolvedModule> packageEntry : packageLookup.entrySet()) {
            ResolvedModuleAccess moduleAccess = getAsResolvedModule(packageEntry.getValue());
            if(moduleName.equals(moduleAccess.name())) {
                if(existed) packages.add(packageEntry.getKey());
                else moduleAccess.setName(newModuleName);
            }
        }
        if(existed)
            for(String pkg : packages) packageLookup.put(pkg,existingModule.accessAs());
    }
    
    public ConfigurationAccess configuration() {
        Object configuration = getDirect("configuration");
        if(Objects.nonNull(configuration)) return getConfiguration(configuration);
        logOrPrintError("Configuration field not found in ModuleClassLoader "+this.access());
        return null;
    }
    
    @Override public Collection<ModuleHolder> getAllReferents() {
        return Arrays.asList(this,configuration(),getModuleLayer());
    }
    
    ResolvedModuleAccess getAsResolvedModule(Object resolvedModule) {
        if(Objects.isNull(resolvedModule)) return null;
        if(resolvedModule instanceof ResolvedModuleAccess) return (ResolvedModuleAccess)resolvedModule;
        return ModuleSystemAccessor.getResolvedModule(resolvedModule,this);
    }
    
    public ModuleLayerAccess getModuleLayer() {
        if(Objects.isNull(this.layer)) {
            logOrPrintError("Cannot get ModuleLayer! (ModuleClassLoaderAccess#layerName is null)");
            return null;
        }
        return NeoforgeModuleAccess.getModuleLayer(this.layer,this);
    }
    
    public ResolvedModuleAccess getResolvedModule(String pkg) {
        return getAsResolvedModule(packageLookup().get(pkg));
    }
    
    @IndirectCallers
    public String getResolvedModuleName(Object resolvedModule) {
        return getAsResolvedModule(resolvedModule).name();
    }
    
    public ModuleReferenceAccess getRoot(String name) {
        ModuleReference moduleReference = resolvedRoots().get(name);
        return Objects.nonNull(moduleReference) ? getModuleReference(moduleReference) : null;
    }
    
    public ModuleReference getRootDirect(String name) {
        return resolvedRoots().get(name);
    }
    
    @IndirectCallers
    public ModuleLayerHandlerAccess handler() {
        return NeoforgeModuleAccess.getModuleLayerHandler(this);
    }
    
    public String layerName() {
        return Objects.nonNull(this.layer) ? this.layer.name() : "UNKNOWN";
    }
    
    /**
     * Get a ResolvedModule from the packageLookup map that matches the input moduleName
     * Remove all packages associated with the module from the packageLookup
     */
    public void lookupAndRemovePackagesFor(String moduleName) {
        removePackages(lookupResolvedModule(moduleName));
    }
    
    /**
     * Get a ResolvedModule from the packageLookup map that matches the input moduleName
     */
    public ResolvedModuleAccess lookupResolvedModule(String moduleName) {
        for(Object resolvedModule : packageLookup().values()) {
            ResolvedModuleAccess moduleAccess = getAsResolvedModule(resolvedModule);
            if(moduleName.equals(moduleAccess.name())) return moduleAccess;
        }
        return null;
    }
    
    @IndirectCallers
    public void moveModulesTo(Layer targetLayer, String ... moduleNames) {
        ModuleClassLoaderAccess targetLoader = NeoforgeModuleAccess.getModuleClassLoader(targetLayer,this);
        for(String moduleName : moduleNames) moveModuleTo(targetLoader,moduleName);
    }
    
    @IndirectCallers
    public void moveModuleTo(Layer targetLayer, String moduleName) {
        moveModuleTo(NeoforgeModuleAccess.getModuleClassLoader(targetLayer,this),moduleName);
    }
    
    /**
     * Assumes layer has been set for both this and the targetLoader
     */
    public void moveModuleTo(ModuleClassLoaderAccess targetLoader, String moduleName) {
        ResolvedModuleAccess resolvedModule = lookupResolvedModule(moduleName);
        resolvedModule.configuration().moveModuleTo(targetLoader.configuration(),resolvedModule);
        movePackageLookup(targetLoader,resolvedModule);
        moveRoots(targetLoader,moduleName);
        NeoforgeModuleAccess.moveModule(getModuleLayer(),targetLoader.layer,moduleName);
    }
    
    private void movePackageLookup(ModuleClassLoaderAccess targetLoader, ResolvedModuleAccess resolvedModule) {
        Set<String> packages = new HashSet<>();
        Map<String,ResolvedModule> packageLookup = packageLookup();
        for(Entry<String,ResolvedModule> packageEntry : packageLookup.entrySet())
            if(packageEntry.getValue()==resolvedModule.access()) packages.add(packageEntry.getKey());
        Map<String,ResolvedModule> targetPackageLookup = targetLoader.packageLookup();
        for(String pkg : packages) {
            packageLookup.remove(pkg);
            targetPackageLookup.put(pkg,resolvedModule.accessAs());
        }
        movePackageParent(targetLoader,packages);
    }
    
    private void movePackageParent(ModuleClassLoaderAccess targetLoader, Set<String> packages) {
        if(packages.isEmpty()) return;
        if(this.layer!=BOOT) addParentLoaders(packages,targetLoader);
        targetLoader.removeParentLoaders(packages);
    }
    
    private void moveRoots(ModuleClassLoaderAccess targetLoader, String moduleName) {
        ModuleReference ref = getRoot(moduleName).accessAs();
        if(Objects.nonNull(ref)) {
            removeRoot(moduleName);
            targetLoader.addRoot(moduleName,ref);
        }
    }
    
    @IndirectCallers
    public ResolvedModuleAccess newResolvedModule(ModuleReferenceAccess moduleReference) {
        return newResolvedModule(Objects.nonNull(moduleReference) ? moduleReference.access() : null);
    }
    
    public ResolvedModuleAccess newResolvedModule(Object moduleReference) {
        ConfigurationAccess configuration = configuration();
        return newResolvedModule(Objects.nonNull(configuration) ? configuration.access() : null,moduleReference);
    }
    
    public ResolvedModuleAccess newResolvedModule(Object configuration, Object moduleReference) {
        if(Objects.isNull(configuration)) {
            logOrPrintError("Cannot create new ResolvedModule with null Configuration!");
            return null;
        }
        if(Objects.isNull(moduleReference)) {
            logOrPrintError("Cannot create new ResolvedModule with null ModuleReference!");
            return null;
        }
        return newResolvedModule(configuration,moduleReference);
    }
    
    public Map<String,ResolvedModule> packageLookup() {
        return getDirect("packageLookup");
    }
    
    public Map<String,ClassLoader> parentLoaders() {
        return getDirect("parentLoaders");
    }
    
    public void removeModule(String moduleName) {
        removeRoot(moduleName);
        lookupAndRemovePackagesFor(moduleName);
    }
    
    public void removePackages(ResolvedModuleAccess resolvedModule) {
        if(Objects.nonNull(resolvedModule)) removePackages(resolvedModule.packages());
    }
    
    public void removePackages(Collection<String> pkgs) {
        if(Objects.isNull(pkgs) || pkgs.isEmpty()) return;
        for(Map<String,?> map : Arrays.asList(packageLookup(),parentLoaders()))
            for(String pkg : pkgs) map.remove(pkg);
    }
    
    @IndirectCallers
    public void removePackage(String pkg) {
        removePackageLookup(pkg);
        removeParentLoader(pkg);
    }
    
    public void removePackageLookup(String pkg) {
        packageLookup().remove(pkg);
    }
    
    public void removePackagesForModule(ResolvedModuleAccess resolvedModule) {
        removePackagesForModule((ResolvedModule)resolvedModule.accessAs());
    }
    
    public void removePackagesForModule(ResolvedModule resolvedModule) {
        packageLookup().entrySet().removeIf(entry -> entry.getValue().equals(resolvedModule));
    }
    
    public void removeParentLoaders(Collection<String> pkgs) {
        Map<String,ClassLoader> parentLoaders = parentLoaders();
        for(String pkg : pkgs) parentLoaders.remove(pkg);
    }
    
    public void removeParentLoader(String pkg) {
        parentLoaders().remove(pkg);
    }
    
    public void removeRoot(String root) {
        resolvedRoots().remove(root);
    }
    
    @IndirectCallers
    public Map<String,ModuleReference> resolvedRoots() {
        return getDirect("resolvedRoots");
    }
}