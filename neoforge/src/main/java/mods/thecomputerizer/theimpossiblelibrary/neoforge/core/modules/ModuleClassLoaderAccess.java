package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules;

import cpw.mods.cl.ModuleClassLoader;
import cpw.mods.modlauncher.api.IModuleLayerManager.Layer;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ClassLoaderAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ConfigurationAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleDescriptorAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleHolder;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleLayerAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleReferenceAccess;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleSystemAccessor;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ResolvedModuleAccess;
import org.jetbrains.annotations.Nullable;

import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleReference;
import java.lang.module.ResolvedModule;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

/**
 * [1.18.2+] cpw.mods.cl.ModuleClassLoader
 * [1.20.4+] extends net.minecraftforge.securemodules.SecureModuleClassLoader
 */
public class ModuleClassLoaderAccess extends ClassLoaderAccess implements ModuleHolder {
    
    @Setter Layer layer;
    
    ModuleClassLoaderAccess(ModuleClassLoader loader, Object accessorOrLogger) {
        super(loader,accessorOrLogger);
    }
    
    @IndirectCallers
    public void addPackage(String pkg, ResolvedModuleAccess resolvedModule) {
        addPackage(pkg,(ResolvedModule)resolvedModule.accessAs());
    }
    
    void addPackage(String pkg, ResolvedModule resolvedModule) {
        packageLookup().put(pkg,resolvedModule);
    }
    
    @IndirectCallers
    public void addPackages(ResolvedModuleAccess resolvedModule) {
        addPackages(resolvedModule.reference().descriptor(),resolvedModule);
    }
    
    public void addPackages(ModuleDescriptorAccess moduleDescriptor, ResolvedModuleAccess resolvedModule) {
        addPackages(moduleDescriptor.packages(),resolvedModule);
    }
    
    public void addPackages(Collection<String> pkgs, ResolvedModuleAccess resolvedModule) {
        addPackages(pkgs,(ResolvedModule)resolvedModule.accessAs());
    }
    
    public void addPackages(Collection<String> pkgs, ResolvedModule resolvedModule) {
        Map<String,ResolvedModule> packageLookup = packageLookup();
        for(String pkg : pkgs) packageLookup.put(pkg,resolvedModule);
    }
    
    @IndirectCallers
    public void addPackagesFrom(Collection<String> pkgs, ModuleClassLoaderAccess source, String moduleName) {
        ResolvedModule module = source.getConfigModuleDirect(moduleName);
        addPackages(pkgs,module);
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
    
    @IndirectCallers
    public void addRoot(ResolvedModuleAccess resolvedModule) {
        addRoot(resolvedModule.reference());
    }
    
    public void addRoot(ModuleReferenceAccess moduleReference) {
        addRoot(moduleReference.name(),(ModuleReference)moduleReference.accessAs());
    }
    
    @IndirectCallers
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
    
    /**
     * Moves the packages from a module to a module with a different name.
     * Add the packages if the module already exists or rename the original module if it does not
     */
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
    
    /**
     * Assumes the combined module already has a defined root
     */
    public void combineModules(URI combinedLocation, String combinedName, String ... others) {
        ConfigurationAccess configuration = configuration();
        ResolvedModuleAccess module = configuration.getModule(combinedName);
        if(Objects.isNull(module)) {
            this.logger.error("Cannot combined modules {} into module {} that does not exist!",others,
                              combinedLocation);
            return;
        }
        ModuleLayerAccess layer = getModuleLayer();
        ModuleReferenceAccess reference = getRoot(combinedName);
        if(Objects.nonNull(reference)) reference.setLocation(combinedLocation);
        module.inheritFrom(configuration,others);
        Map<String,ClassLoader> parentLoaders = parentLoaders();
        Map<String,ResolvedModule> packageLookup = packageLookup();
        for(String pkg : module.packages()) {
            packageLookup.put(pkg,module.accessAs());
            parentLoaders.remove(pkg);
        }
        removeRoots(others);
        configuration.removeModules(others);
        layer.combineModules(combinedName,others);
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
    
    public <T> T getConfigModuleDirect(String name) {
        return configuration().getModuleDirect(name);
    }
    
    public @Nullable ModuleDescriptorAccess getModuleDescriptor(String name) {
        ModuleAccess module = getModuleLayer().getModule(name);
        return Objects.nonNull(module) ? module.getDescriptor() : null;
    }
    
    public @Nullable ModuleDescriptor getModuleDescriptorDirect(String name) {
        ModuleDescriptorAccess descriptorAccess = getModuleDescriptor(name);
        return Objects.nonNull(descriptorAccess) ? descriptorAccess.accessAs() : null;
    }
    
    public ModuleLayerAccess getModuleLayer() {
        if(Objects.isNull(this.layer)) {
            logOrPrintError("Cannot get ModuleLayer! (ModuleClassLoaderAccess#layerName is null)");
            return null;
        }
        return NeoforgeModuleAccess.getModuleLayer(this.layer,this);
    }
    
    @IndirectCallers
    public Set<String> getModulePackages(String name) {
        ModuleDescriptorAccess descriptor = getModuleDescriptor(name);
        return Objects.nonNull(descriptor) ? descriptor.packages() : Set.of();
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
    
    @IndirectCallers
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
    
    @IndirectCallers
    public Set<ResolvedModule> lookupModules(Collection<String> packages) {
        Set<ResolvedModule> modules = new HashSet<>();
        for(Entry<String,ResolvedModule> lookupEntry : packageLookup().entrySet())
            if(packages.contains(lookupEntry.getKey())) modules.add(lookupEntry.getValue());
        return modules;
    }
    
    @IndirectCallers
    public Set<String> lookupPackagesFor(String ... moduleNames) {
        Set<String> pkgs = new HashSet<>();
        for(Entry<String,ResolvedModule> lookupEntry : packageLookup().entrySet()) {
            String entryName = lookupEntry.getValue().name();
            for(String moduleName : moduleNames) {
                if(moduleName.equals(entryName)) {
                    pkgs.add(lookupEntry.getKey());
                    break;
                }
            }
        }
        return pkgs;
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
    
    /**
     * Assumes layer has been set for both this and the targetLoader
     */
    public void moveModuleTo(ModuleClassLoaderAccess targetLoader, String moduleName) {
        ResolvedModuleAccess resolvedModule = lookupResolvedModule(moduleName);
        resolvedModule.configuration().moveModuleTo(targetLoader.configuration(),resolvedModule);
        moveRoots(targetLoader,moduleName);
        movePackageLookup(targetLoader,resolvedModule);
    }
    
    private void movePackageLookup(ModuleClassLoaderAccess targetLoader, ResolvedModuleAccess resolvedModule) {
        Set<String> packages = resolvedModule.packages(true);
        movePackageParent(targetLoader,packages);
        Map<String,ResolvedModule> packageLookup = packageLookup();
        Map<String,ResolvedModule> targetPackageLookup = targetLoader.packageLookup();
        for(String pkg : packages) {
            packageLookup.remove(pkg);
            targetPackageLookup.put(pkg,resolvedModule.accessAs());
        }
    }
    
    private void movePackageParent(ModuleClassLoaderAccess targetLoader, Set<String> packages) {
        if(packages.isEmpty()) return;
        addParentLoaders(packages,targetLoader);
        targetLoader.removeParentLoaders(packages);
    }
    
    private void moveRoots(ModuleClassLoaderAccess targetLoader, String moduleName) {
        ModuleReference ref = getRootDirect(moduleName);
        if(Objects.nonNull(ref)) {
            removeRoot(moduleName);
            targetLoader.addRoot(moduleName,ref);
        }
    }
    
    @IndirectCallers
    public void moveServicesTo(ModuleLayerAccess target, ModuleAccess module, String ... serviceMovementBlacklist) {
        getModuleLayer().moveServicesTo(target,module,serviceMovementBlacklist);
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
    
    public void removeRoots(String ... roots) {
        Map<String,ModuleReference> resolvedRoots = resolvedRoots();
        for(String root : roots) resolvedRoots.remove(root);
    }
    
    public void renameModule(String name, String newName) {
        ConfigurationAccess configuration = configuration();
        ResolvedModuleAccess module = configuration.getModule(name);
        if(Objects.isNull(module)) {
            this.logger.error("Cannot rename module {} that does not exist on layer {}!",name,this.layer);
            return;
        }
        ModuleLayerAccess layer = getModuleLayer();
        module.setName(newName);
        ModuleReference root = getRootDirect(name);
        if(Objects.nonNull(root)) {
            removeRoot(name);
            addRoot(newName,root);
        }
        configuration.renameModule(name,newName);
        layer.renameModule(name,newName);
    }
    
    @IndirectCallers
    public Map<String,ModuleReference> resolvedRoots() {
        return getDirect("resolvedRoots");
    }
    
    @Override public String toString() {
        String layerVal = String.valueOf(this.layer);
        return "ModuleClassLoaderAccess["+layerVal+"]";
    }
}