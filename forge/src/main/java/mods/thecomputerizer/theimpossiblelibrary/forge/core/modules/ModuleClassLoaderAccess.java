package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader.SECURE_CLASSLOADER_FORMAT;

/**
 * [1.18.2+] cpw.mods.cl.ModuleClassLoader
 * [1.20.4+] extends net.minecraftforge.securemodules.SecureModuleClassLoader
 */
public class ModuleClassLoaderAccess extends ClassLoaderAccess implements ModuleHolder {
    
    static final String packageLookupField = changing("packageToOurModules","packageLookup");
    static final String parentLoadersField = changing("packageToParentLoader","parentLoaders");
    static final String resolvedRootsField = changing("ourModules","resolvedRoots");
    
    @Setter String layerName;
    
    ModuleClassLoaderAccess(ClassLoader loader, Object accessorOrLogger) {
        super(loader,accessorOrLogger);
    }
    
    public void addPackages(ResolvedModuleAccess resolvedModule) {
        addPackages(resolvedModule.reference().descriptor(),resolvedModule);
    }
    
    public void addPackages(ModuleDescriptorAccess moduleDescriptor, ResolvedModuleAccess resolvedModule) {
        addPackages(moduleDescriptor.packages(),resolvedModule);
    }
    
    public void addPackages(Collection<String> pkgs, ResolvedModuleAccess resolvedModule) {
        addPackages(pkgs,resolvedModule.access);
    }
    
    void addPackages(Collection<String> pkgs, Object resolvedModule) {
        Map<String,Object> packageLookup = packageLookup();
        for(String pkg : pkgs) packageLookup.put(pkg,resolvedModule);
    }
    
    @IndirectCallers
    public void addPackage(String pkg, ResolvedModuleAccess resolvedModule) {
        addPackage(pkg,resolvedModule.access);
    }
    
    void addPackage(String pkg, Object resolvedModule) {
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
        addParentLoaders(pkgs,loader.access);
    }
    
    void addParentLoaders(Collection<String> pkgs, Object loader) {
        Map<String,Object> parentLoaders = parentLoaders();
        for(String pkg : pkgs) parentLoaders.put(pkg,loader);
    }
    
    @IndirectCallers
    public void addParentLoader(String pkg, ModuleClassLoaderAccess loader) {
        addParentLoader(pkg,loader.access);
    }
    
    void addParentLoader(String pkg, Object loader) {
        parentLoaders().put(pkg,loader);
    }
    
    public void addRoot(ResolvedModuleAccess resolvedModule) {
        addRoot(resolvedModule.reference());
    }
    
    public void addRoot(ModuleReferenceAccess moduleReference) {
        addRoot(moduleReference.name(),moduleReference.access);
    }
    
    public void addRoot(String name, ModuleReferenceAccess moduleReference) {
        addRoot(name,moduleReference.access);
    }
    
    public void addRoot(String name, Object moduleReference) {
        resolvedRoots().put(name,moduleReference);
    }
    
    @Override public void cloneModule(String moduleName, String newModuleName) {
        clonePackages(moduleName,newModuleName);
        cloneModuleMap(resolvedRoots(),moduleName,newModuleName);
        if(SECURE_CLASSLOADER_FORMAT)
            cloneModuleMap(ourModulesSecure(),moduleName,newModuleName);
    }
    
    void cloneModuleMap(Map<String,Object> map, String moduleName, String newModuleName) {
        if(map.containsKey(moduleName)) {
            Object o = map.get(moduleName);
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
        Map<String,Object> packageLookup = packageLookup();
        for(Entry<String,Object> packageEntry : packageLookup.entrySet()) {
            ResolvedModuleAccess moduleAccess = getAsResolvedModule(packageEntry.getValue());
            if(moduleName.equals(moduleAccess.name())) {
                if(existed) packages.add(packageEntry.getKey());
                else moduleAccess.setName(newModuleName);
            }
        }
        if(existed)
            for(String pkg : packages) packageLookup.put(pkg,existingModule.access);
    }
    
    public ConfigurationAccess configuration() {
        Object configuration = getDirect("configuration");
        if(Objects.nonNull(configuration)) return getConfiguration(configuration);
        logOrPrintError("Configuration field not found in ModuleClassLoader "+this.access);
        return null;
    }
    
    @Override public Collection<ModuleHolder> getAllReferents() {
        return Arrays.asList(this,configuration(),getModuleLayer());
    }
    
    ResolvedModuleAccess getAsResolvedModule(Object resolvedModule) {
        if(Objects.isNull(resolvedModule)) return null;
        if(resolvedModule instanceof ResolvedModuleAccess) return (ResolvedModuleAccess)resolvedModule;
        return new ResolvedModuleAccess(resolvedModule,this);
    }
    
    public ModuleLayerAccess getModuleLayer() {
        if(Objects.isNull(this.layerName)) {
            logOrPrintError("Cannot get ModuleLayer! (ModuleClassLoaderAccess#layerName is null)");
            return null;
        }
        return getModuleLayer(this.layerName);
    }
    
    public ResolvedModuleAccess getResolvedModule(String pkg) {
        return getAsResolvedModule(packageLookup().get(pkg));
    }
    
    @IndirectCallers
    public String getResolvedModuleName(Object resolvedModule) {
        return getAsResolvedModule(resolvedModule).name();
    }
    
    public ModuleReferenceAccess getRoot(String name) {
        Object moduleReference = resolvedRoots().get(name);
        return Objects.nonNull(moduleReference) ? getModuleReference(moduleReference) : null;
    }
    
    @IndirectCallers
    public ModuleLayerHandlerAccess handler() {
        return getModuleLayerHandler();
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
    public void moveModulesTo(String targetLayerName, String ... moduleNames) {
        ModuleClassLoaderAccess targetLoader = getModuleClassLoader(targetLayerName);
        for(String moduleName : moduleNames) moveModuleTo(targetLoader,moduleName);
    }
    
    @IndirectCallers
    public void moveModuleTo(String targetLayerName, String moduleName) {
        moveModuleTo(getModuleClassLoader(targetLayerName),moduleName);
    }
    
    /**
     * Assumes layerName has been set for both this and the targetLoader
     */
    public void moveModuleTo(ModuleClassLoaderAccess targetLoader, String moduleName) {
        ResolvedModuleAccess resolvedModule = lookupResolvedModule(moduleName);
        resolvedModule.configuration().moveModuleTo(targetLoader.configuration(),resolvedModule);
        movePackageLookup(targetLoader,resolvedModule);
        moveRoots(targetLoader,moduleName);
        getModuleLayer().moveModule(targetLoader.layerName,moduleName);
    }
    
    private void movePackageLookup(ModuleClassLoaderAccess targetLoader, ResolvedModuleAccess resolvedModule) {
        Set<String> packages = new HashSet<>();
        Map<String,Object> packageLookup = packageLookup();
        for(Entry<String,Object> packageEntry : packageLookup.entrySet())
            if(packageEntry.getValue()==resolvedModule.access) packages.add(packageEntry.getKey());
        Map<String,Object> targetPackageLookup = targetLoader.packageLookup();
        for(String pkg : packages) {
            packageLookup.remove(pkg);
            targetPackageLookup.put(pkg,resolvedModule);
        }
        movePackageParent(targetLoader,packages);
    }
    
    private void movePackageParent(ModuleClassLoaderAccess targetLoader, Set<String> packages) {
        if(packages.isEmpty()) return;
        if(!"BOOT".equals(this.layerName)) addParentLoaders(packages,targetLoader);
        targetLoader.removeParentLoaders(packages);
    }
    
    private void moveRoots(ModuleClassLoaderAccess targetLoader, String moduleName) {
        Object ref = getRoot(moduleName);
        if(Objects.nonNull(ref)) {
            removeRoot(moduleName);
            targetLoader.addRoot(moduleName,ref);
        }
    }
    
    @IndirectCallers
    public ResolvedModuleAccess newResolvedModule(ModuleReferenceAccess moduleReference) {
        return newResolvedModule(Objects.nonNull(moduleReference) ? moduleReference.access : null);
    }
    
    public ResolvedModuleAccess newResolvedModule(Object moduleReference) {
        ConfigurationAccess configuration = configuration();
        return newResolvedModule(Objects.nonNull(configuration) ? configuration.access : null,moduleReference);
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
    
    /**
     * Only present in 1.20.4+ but still needs to be accounted for
     */
    public Map<String,Object> ourModulesSecure() {
        return SECURE_CLASSLOADER_FORMAT ? getDirect("ourModulesSecure") : Collections.emptyMap();
    }
    
    @IndirectCallers
    public Map<String,Object> packageLookup() {
        return getDirect(packageLookupField);
    }
    
    public Map<String,Object> packageToCodeSource() {
        return SECURE_CLASSLOADER_FORMAT ? getDirect("packageToCodeSource") : Collections.emptyMap();
    }
    
    @IndirectCallers
    public Map<String,Object> parentLoaders() {
        return getDirect(parentLoadersField);
    }
    
    public void removeModule(String moduleName) {
        removeRoot(moduleName);
        removeSecureModule(moduleName);
        lookupAndRemovePackagesFor(moduleName);
    }
    
    public void removePackages(ResolvedModuleAccess resolvedModule) {
        if(Objects.nonNull(resolvedModule)) removePackages(resolvedModule.packages());
    }
    
    public void removePackages(Collection<String> pkgs) {
        if(Objects.isNull(pkgs) || pkgs.isEmpty()) return;
        Collection<Map<String,Object>> maps = SECURE_CLASSLOADER_FORMAT ?
                Arrays.asList(packageLookup(),parentLoaders(),packageToCodeSource()) :
                Arrays.asList(packageLookup(),parentLoaders());
        for(Map<String,Object> map : maps)
            for(String pkg : pkgs) map.remove(pkg);
    }
    
    @IndirectCallers
    public void removePackage(String pkg) {
        removePackageLookup(pkg);
        removeParentLoader(pkg);
        removePackageToCodeSource(pkg);
    }
    
    public void removePackageLookup(String pkg) {
        packageLookup().remove(pkg);
    }
    
    public void removePackageToCodeSource(String pkg) {
        if(SECURE_CLASSLOADER_FORMAT) packageToCodeSource().remove(pkg);
    }
    
    public void removePackagesForModule(ResolvedModuleAccess resolvedModule) {
        removePackagesForModule(resolvedModule.access);
    }
    
    public void removePackagesForModule(Object resolvedModule) {
        packageLookup().entrySet().removeIf(entry -> entry.getValue().equals(resolvedModule));
    }
    
    public void removeParentLoaders(Collection<String> pkgs) {
        Map<String,Object> parentLoaders = parentLoaders();
        for(String pkg : pkgs) parentLoaders.remove(pkg);
    }
    
    public void removeParentLoader(String pkg) {
        parentLoaders().remove(pkg);
    }
    
    public void removeRoot(String root) {
        resolvedRoots().remove(root);
    }
    
    public void removeSecureModule(String moduleName) {
        if(SECURE_CLASSLOADER_FORMAT) ourModulesSecure().remove(moduleName);
    }
    
    @IndirectCallers
    public Map<String,Object> resolvedRoots() {
        return getDirect(resolvedRootsField);
    }
}