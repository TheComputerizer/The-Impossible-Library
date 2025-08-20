package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * java.lang.module.Configuration
 */
public class ConfigurationAccess extends AbstractModuleSystemAccessor implements ModuleHolder {
    
    final Collection<ModuleHolder> referents;
    
    ConfigurationAccess(Object configuration, Object accessorOrLogger) {
        super(configuration,accessorOrLogger);
        this.referents = Collections.singleton(this);
    }
    
    @Override public Collection<ModuleHolder> getAllReferents() {
        return this.referents;
    }
    
    /**
     * There are no additional referents to account for in a ModuleLayer
     */
    @Override public Collection<ModuleHolder> getLayeredReferents() {
        return this.referents;
    }
    
    public void addModule(ResolvedModuleAccess resolvedModule) {
        addModule(resolvedModule.access);
    }
    
    private void addModule(Object resolvedModule) {
       Set<Object> modules = modules();
       modules.add(resolvedModule);
       setModules(modules);
    }
    
    public void addModuleForced(ResolvedModuleAccess resolvedModule) {
        Object moduleAccess = resolvedModule.access;
        addModule(moduleAccess);
        Map<String,Object> nameToModule = nameToModule();
        nameToModule.put(resolvedModule.name(),moduleAccess);
        setNameToModule(nameToModule);
        resolvedModule.setConfiguration(this);
    }
    
    public void addModuleIfAbsent(ResolvedModuleAccess resolvedModule) {
        addModuleIfAbsent(resolvedModule.name(),resolvedModule);
    }
    
    public void addModuleIfAbsent(String name, ResolvedModuleAccess resolvedModule) {
        addModuleIfAbsent(name,resolvedModule.access);
        resolvedModule.setConfiguration(this);
    }
    
    private void addModuleIfAbsent(String name, Object resolvedModule) {
        Map<String,Object> nameToModule = nameToModule();
        if(!nameToModule.containsKey(name)) {
            nameToModule.put(name,resolvedModule);
            setNameToModule(nameToModule);
        }
    }
    
    public ResolvedModuleAccess asResolvedModule(Object resolvedModule) {
        return resolvedModule instanceof ResolvedModuleAccess ?
                (ResolvedModuleAccess)resolvedModule : getResolvedModule(resolvedModule);
    }
    
    public ResolvedModuleAccess getModule(String moduleName) {
        return getResolvedModule(getModuleDirect(moduleName));
    }
    
    public Object getModuleDirect(String moduleName) {
        return nameToModule(false).get(moduleName);
    }
    
    @IndirectCallers
    public Map<Object,Set<Object>> graph() {
        return graph(true);
    }
    
    public Map<Object,Set<Object>> graph(boolean modifiable) {
        Map<Object,Set<Object>> graph = getDirect("graph");
        return modifiable ? new HashMap<>(graph) : graph;
    }
    
    @IndirectCallers
    public Set<Object> modules() {
        return modules(true);
    }
    
    public Set<Object> modules(boolean modifiable) {
        Set<Object> parents = invoke("modules");
        return modifiable ? new HashSet<>(parents) : parents;
    }
    
    public void moveModuleTo(ConfigurationAccess targetConfiguration, ResolvedModuleAccess resolvedModule) {
        removeModuleFully(resolvedModule);
        targetConfiguration.addModuleForced(resolvedModule);
    }
    
    @IndirectCallers
    public Map<String,Object> nameToModule() {
        return nameToModule(true);
    }
    
    public Map<String,Object> nameToModule(boolean modifiable) {
        Map<String,Object> nameToModule = getDirect("nameToModule");
        return modifiable ? new HashMap<>(nameToModule) : nameToModule;
    }
    
    public ResolvedModuleAccess newResolvedModule(ModuleReferenceAccess moduleReference) {
        return newResolvedModule(this,moduleReference);
    }
    
    @IndirectCallers
    public List<Object> parents() {
        return parents(true);
    }
    
    public List<Object> parents(boolean modifiable) {
        List<Object> parents = invoke("parents");
        return modifiable ? new ArrayList<>(parents) : parents;
    }
    
    public void removeFromGraph(ResolvedModuleAccess resolvedModule) {
        removeFromGraph(resolvedModule.name());
    }
    
    public void removeFromGraph(String moduleName) {
        Map<Object,Set<Object>> graph = graph();
        Map<Object,Set<Object>> graphWithRemovals = new HashMap<>();
        for(Entry<Object,Set<Object>> entry : graph.entrySet()) {
            Object key = entry.getKey();
            ResolvedModuleAccess keyAccess = asResolvedModule(key);
            if(moduleName.equals(keyAccess.name())) continue;
            Set<Object> values = new HashSet<>(entry.getValue());
            values.removeIf(value -> moduleName.equals(asResolvedModule(value).name()));
            if(!values.isEmpty()) graphWithRemovals.put(key,Collections.unmodifiableSet(values));
        }
        graph.clear();
        setGraph(graphWithRemovals);
    }
    
    /**
     * Returns the module that was removed or null if nothing was removed
     */
    public Object removeFromModuleMap(String moduleName) {
        Map<String,Object> nameToModule = nameToModule();
        Object resolvedModule = nameToModule.remove(moduleName);
        setNameToModule(nameToModule);
        return resolvedModule;
    }
    
    public void removeFromModules(Object module) {
        Set<Object> modules = modules();
        modules.remove(module);
        setModules(modules);
    }
    
    /**
     * Remove from modules, moduleToName, and graph
     */
    public void removeModule(ResolvedModuleAccess resolvedModule) {
        removeModule(resolvedModule.name());
    }
    
    /**
     * Remove from modules, moduleToName, and graph
     */
    public void removeModule(String moduleName) {
        removeFromModules(removeFromModuleMap(moduleName));
        removeFromGraph(moduleName);
    }
    
    public void removeModuleFully(ResolvedModuleAccess resolvedModule) {
        removeFromModules(resolvedModule.access);
        String name = resolvedModule.name();
        removeFromModuleMap(name);
        removeFromGraph(name);
    }
    
    public void setGraph(Map<Object,Set<Object>> graph) {
        setDirect("graph",Collections.unmodifiableMap(graph));
    }
    
    public void setModules(Set<Object> modules) {
        setDirect("modules",Collections.unmodifiableSet(modules));
    }
    
    public void setNameToModule(Map<String,Object> nameToModule) {
        setDirect("nameToModule",Collections.unmodifiableMap(nameToModule));
    }
    
    public void setParents(List<Object> parents) {
        setDirect("parents",Collections.unmodifiableList(parents));
    }
}