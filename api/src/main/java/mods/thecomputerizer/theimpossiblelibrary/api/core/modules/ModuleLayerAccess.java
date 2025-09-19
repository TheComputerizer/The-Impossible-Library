package mods.thecomputerizer.theimpossiblelibrary.api.core.modules;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * java.lang.ModuleLayer
 */
public class ModuleLayerAccess extends AbstractModuleSystemAccessor implements ModuleHolder {
    
    final Collection<ModuleHolder> referents;
    @Setter @Getter String layerName; //Could be set for logging but it's not required
    
    ModuleLayerAccess(Object moduleLayer, Object accessorOrLogger) {
        super(moduleLayer,accessorOrLogger);
        this.referents = Collections.singleton(this);
    }
    
    public void addModule(ModuleAccess module) {
        addModule(module.getName(),module);
    }
    
    public void addModule(String name, ModuleAccess module) {
        addModule(name,module.access);
    }
    
    public void addModule(String name, Object module) {
        Map<String,Object> nameToModule = nameToModule();
        nameToModule.put(name,module);
        setNameToModule(nameToModule);
        Set<Object> modules = modules(true);
        modules.add(module);
        setModules(modules);
    }
    
    @Override public void cloneModule(String moduleName, String newModuleName) {
        cloneModule(null,moduleName,newModuleName);
    }
    
    public void cloneModule(ClassLoaderAccess loader, String moduleName, String newModuleName) {
        Map<String,Object> nameToModule = nameToModule();
        Set<Object> modules = modules(true);
        ModuleAccess module = null;
        if(nameToModule.containsKey(moduleName)) {
            Object existing = nameToModule.get(newModuleName);
            module = getModule(moduleName);
            if(Objects.nonNull(existing)) getModule(existing).inheritFrom(module);
            else {
                module.setName(newModuleName);
                nameToModule.put(newModuleName,nameToModule.get(moduleName));
            }
            setNameToModule(nameToModule);
        }
        if(Objects.nonNull(module)) {
            modules.removeIf(m -> {
                String name = getModuleName(m);
                return name.equals(moduleName) || name.equals(newModuleName);
            });
            modules.add(module.access);
            if(Objects.nonNull(loader)) loader.moveModuleClasses(module,moduleName,newModuleName);
        } else {
            module = getModuleFromSet(newModuleName);
            Set<Object> removals = new HashSet<>();
            boolean existed = Objects.nonNull(module);
            boolean changed = false;
            for(Object m : modules) {
                ModuleAccess mAccess = getModule(m);
                if(mAccess.getName().equals(moduleName)) {
                    changed = true;
                    if(Objects.nonNull(module)) {
                        module.inheritFrom(mAccess);
                        removals.add(mAccess.access);
                    }
                    else {
                        module = mAccess;
                        module.setName(newModuleName);
                    }
                }
            }
            if(Objects.nonNull(module) && Objects.nonNull(loader))
                loader.moveModuleClasses(module,moduleName,newModuleName);
            if(changed) {
                modules.removeAll(removals);
                if(!existed) modules.add(module.access);
                setModules(modules);
            }
        }
    }
    
    public void combineModules(String combinedName, String ... others) {
        ModuleAccess combinedModule = getModule(combinedName);
        if(Objects.isNull(combinedModule)) return;
        for(String other : others) {
            ModuleAccess otherModule = getModule(other);
            if(Objects.nonNull(otherModule)) {
                combinedModule.inheritFrom(otherModule);
                removeModule(other);
            }
        }
    }
    
    @IndirectCallers
    public ConfigurationAccess configuration() {
        return getDirect("cf");
    }
    
    public void exportPackagesToAll() {
        Set<Object> modules = modules(true);
        modules.addAll(nameToModule(false).values());
        for(Object module : modules) getModule(module).exportPackagesToAll();
    }
    
    public void findAndAddModule(Object otherLayer, String moduleName, String ... otherValidNames) {
        for(ModuleAccess module : moduleAccessors())
            if(module.getLayer().findAndAddModule(module, otherLayer, moduleName, otherValidNames)) break;
    }
    
    boolean findAndAddModule(ModuleAccess module, Object otherLayer, String moduleName,
            String ... otherValidNames) {
        String name = module.getName();
        if(Objects.isNull(name)) return false;
        boolean equivalentName = name.equals(moduleName);
        if(!equivalentName) {
            for(String validName : otherValidNames) {
                if(validName.equals(moduleName)) {
                    equivalentName = true;
                    break;
                }
            }
        }
        if(equivalentName) {
            ModuleLayerAccess layer = module.getLayer();
            String same = layer.access()==otherLayer ? "the same" : "a different";
            logOrPrint("Found module "+moduleName+" in "+same+" layer that wasn't present in the nameToModule map",
                       Logger::info);
            layer.addModule(moduleName,module);
            return true;
        }
        return false;
    }
    
    @IndirectCallers
    public ClassAccess findClassForModule(String moduleName, String className, boolean initialize) {
        return getClassAccess(className,initialize,findLoader(moduleName));
    }
    
    @IndirectCallers
    public ClassLoader findLoader(ModuleAccess module) {
        return findLoader(module.getName());
    }
    
    public ClassLoader findLoader(String name) {
        return invoke("findLoader",name);
    }
    
    public Optional<Object> findModule(String name) {
        return invoke("findModule",name);
    }
    
    /**
     * Gets all modules from both the modules set and nameToModule map values to account for accidental stragglers
     * Maps each module to its packages and flattens the final result into a set
     */
    @IndirectCallers
    public Map<ModuleAccess,Collection<String>> getAllModulePackages() {
        Set<Object> modules = modules(true);
        modules.addAll(nameToModule(false).values());
        return modules.stream().map(this::getModule).collect(Collectors.toMap(Function.identity(),this::getModulePackages));
    }
    
    @Override public Collection<ModuleHolder> getAllReferents() {
        return this.referents;
    }
    
    public ModuleAccess getAnyModule(String ... names) {
        for(String name : names) {
            ModuleAccess module = getModule(name,false);
            if(Objects.nonNull(module)) return module;
        }
        logOrPrintError("No modules matching any of "+Arrays.toString(names)+" found in layer "+this.layerName);
        return null;
    }
    
    /**
     * There are no additional referents to account for in a ModuleLayer
     */
    @Override public Collection<ModuleHolder> getLayeredReferents() {
        return this.referents;
    }
    
    public ModuleAccess getModule(String name) {
        return getModule(name,true);
    }
    
    public ModuleAccess getModule(String name, boolean logError) {
        Map<String,Object> nameToModule = nameToModule(false);
        Object module = nameToModule.get(name);
        if(Objects.nonNull(module)) return getModule(module);
        String msg = "Module "+name+" not found in layer "+this.layerName;
        if(logError) logOrPrintError(msg);
        else logOrPrint(msg,Logger::debug);
        return null;
    }
    
    private ModuleAccess getModuleFromSet(String name) {
        for(Object module : modules()) {
            ModuleAccess mAccess = getModule(module);
            if(name.equals(mAccess.getName())) return mAccess;
        }
        return null;
    }
    
    String getModuleName(Object module) {
        return getModule(module).getName();
    }
    
    @IndirectCallers
    Collection<String> getModulePackages(Object module) {
        return getModulePackages(getModule(module));
    }
    
    Collection<String> getModulePackages(ModuleAccess module) {
        return module.getPackages();
    }
    
    public ServicesCatalogAccess getServicesCatalog() {
        return getJavaLangAccess().getServicesCatalog(this);
    }
    
    /**
     * It's possible that the module set of a layer contains a module that was removed from the nameToModule map
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean hasMatchingModuleInSet(String name) {
        for(ModuleAccess module : moduleAccessors())
            if(name.equals(module.getName())) return true;
        return false;
    }
    
    public Set<String> moduleNames() {
        return modules().stream().map(this::getModuleName).collect(Collectors.toSet());
    }
    
    public Set<Object> modules() {
        return modules(false);
    }
    
    public Set<Object> modules(boolean modifiable) {
        Set<Object> modules = invoke("modules");
        return modifiable ? new HashSet<>(modules) : modules;
    }
    
    public Set<ModuleAccess> moduleAccessors() {
        return modules().stream().map(this::getModule).collect(Collectors.toSet());
    }
    
    public void moveServicesTo(ModuleLayerAccess target, ModuleAccess module,
            String ... serviceMovementBlacklist) {
        logOrPrint("Moving services from "+this.layerName+" to "+target.layerName,Logger::debug);
        moveServicesTo(target.getServicesCatalog(),module,serviceMovementBlacklist);
    }
    
    public void moveServicesTo(ServicesCatalogAccess target, ModuleAccess module,
            String ... serviceMovementBlacklist) {
        target.inheritProviders(getServicesCatalog(),module.access,module.getName(),
                                Arrays.asList(serviceMovementBlacklist));
    }
    
    @IndirectCallers
    public Map<String,Object> nameToModule() {
        return nameToModule(true);
    }
    
    public Map<String,Object> nameToModule(boolean modifiable) {
        Map<String,Object> nameToModule = getDirect("nameToModule");
        return modifiable ? new HashMap<>(nameToModule) : nameToModule;
    }
    
    public ModuleAccess newModule(ClassLoaderAccess loader, ModuleDescriptorAccess moduleDescriptor,
            URI uri) {
        return newModule(this.access,loader.unwrap(),moduleDescriptor.access,uri);
    }
    
    @IndirectCallers
    public ModuleAccess newModule(ClassLoader loader, Object moduleDescriptor, URI uri) {
        return newModule(this.access,loader,moduleDescriptor,uri);
    }
    
    @IndirectCallers
    public List<Object> parents() {
        return parents(false);
    }
    
    public List<Object> parents(boolean modifiable) {
        List<Object> parents = getDirect("parents");
        return modifiable ? new ArrayList<>(parents) : parents;
    }
    
    /**
     * Returns the instance of the module that was removed or null if the module was not present
     */
    public void removeModule(String moduleName) {
        removeModuleAndReturn(moduleName);
    }
    
    /**
     * Returns the instance of the module that was removed or null if the module was not present
     */
    public ModuleAccess removeModuleAndReturn(String moduleName) {
        Map<String,Object> nameToModule = nameToModule();
        if(!nameToModule.containsKey(moduleName)) return null;
        Object module = nameToModule.get(moduleName);
        nameToModule.remove(moduleName);
        setNameToModule(nameToModule);
        Set<Object> modules = modules(true);
        modules.remove(module);
        setModules(modules);
        return getModule(module);
    }
    
    @IndirectCallers
    void removeModuleFromSet(String moduleName) {
        Set<Object> modules = modules(true);
        modules.removeIf(module -> getModuleName(module).equals(moduleName));
        setModules(modules);
    }
    
    @IndirectCallers
    public void removeServiceImplementations(String serviceName, String impl) {
        getServicesCatalog().removeImplementations(serviceName,impl);
    }
    
    public void renameModule(String name, String newName) {
        ModuleAccess module = removeModuleAndReturn(name);
        if(Objects.nonNull(module)) {
            module.setName(newName);
            addModule(newName,module);
        }
    }
    
    public void setModules(Set<Object> modules) {
        setDirect("modules",Collections.unmodifiableSet(modules));
    }
    
    public void setNameToModule(Map<String,Object> nameToModule) {
        setDirect("nameToModule",nameToModule.isEmpty() ? Collections.emptyMap() : nameToModule);
    }
}