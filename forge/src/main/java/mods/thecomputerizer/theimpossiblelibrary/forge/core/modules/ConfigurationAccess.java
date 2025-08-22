package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
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
    
    /**
     * Merge all ResolvedModule keys with the input name while setting the merged key to the new name
     * Merge the ResolvedModule instances in each value set in the same way
     */
    void cloneGraphModules(String moduleName, String newModuleName) {
        Map<Object,Set<Object>> modifiedGraph = new HashMap<>();
        Set<String> accountedNames = new HashSet<>();
        boolean modified = false;
        for(Entry<Object,Set<Object>> graphEntry : graph(false).entrySet()) {
            ResolvedModuleAccess key = getResolvedModule(graphEntry.getKey());
            String keyName = key.name();
            if(accountedNames.contains(keyName)) continue;
            accountedNames.add(keyName);
            if(moduleName.equals(keyName)) {
                key.setName(newModuleName);
                modified = true;
            }
            Set<Object> values = graphEntry.getValue();
            modified = modified || mergeMatchingNamesTo(values,moduleName,newModuleName);
            modifiedGraph.put(key.access,values);
        }
        if(modified) setGraph(modifiedGraph);
    }
    
    @Override public void cloneModule(String moduleName, String newModuleName) {
        Map<String,Object> nameToModule = nameToModule();
        Set<Object> modules = modules(true);
        ResolvedModuleAccess module = null;
        if(nameToModule.containsKey(moduleName)) {
            Object existing = nameToModule.get(newModuleName);
            module = getModule(moduleName);
            if(Objects.nonNull(existing)) getResolvedModule(existing).inheritFrom(module);
            else {
                module.setName(newModuleName);
                nameToModule.put(newModuleName,nameToModule.get(moduleName));
            }
            setNameToModule(nameToModule);
        }
        if(Objects.nonNull(module)) {
            modules.removeIf(m -> {
                String name = getResolvedModuleName(m);
                return name.equals(moduleName) || name.equals(newModuleName);
            });
            modules.add(module.access);
        } else {
            module = getModuleFromSet(newModuleName);
            Set<Object> removals = new HashSet<>();
            boolean existed = Objects.nonNull(module);
            boolean changed = false;
            for(Object m : modules) {
                ResolvedModuleAccess mAccess = getResolvedModule(m);
                if(mAccess.name().equals(moduleName)) {
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
            if(changed) {
                modules.removeAll(removals);
                if(!existed) modules.add(module.access);
                setModules(modules);
            }
        }
        cloneGraphModules(moduleName,newModuleName);
    }
    
    public String findLayerName() {
        Set<Enum<?>> potentialLayers = getModuleLayerHandler().completedLayers().keySet();
        for(Enum<?> potentialLayer : potentialLayers) {
            String layerName = potentialLayer.toString();
            if(getModuleClassLoader(layerName).configuration().access==this.access) return layerName;
        }
        return "UNKNOWN LAYER";
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
    
    public ResolvedModuleAccess getModule(String moduleName) {
        return getResolvedModule(getModuleDirect(moduleName));
    }
    
    public Object getModuleDirect(String moduleName) {
        return nameToModule(false).get(moduleName);
    }
    
    private ResolvedModuleAccess getModuleFromSet(String name) {
        for(Object module : modules()) {
            ResolvedModuleAccess mAccess = getResolvedModule(module);
            if(name.equals(mAccess.name())) return mAccess;
        }
        return null;
    }
    
    public String getResolvedModuleName(Object resolvedModule) {
        return getResolvedModule(resolvedModule).name();
    }
    
    @IndirectCallers
    public Map<Object,Set<Object>> graph() {
        return graph(true);
    }
    
    public Map<Object,Set<Object>> graph(boolean modifiable) {
        Map<Object,Set<Object>> graph = getDirect("graph");
        return modifiable ? new HashMap<>(graph) : graph;
    }
    
    boolean mergeMatchingNamesTo(Collection<Object> resolvedModules, String name, String newName) {
        Object firstMatch = null;
        Collection<Object> otherMatches = new HashSet<>();
        for(Object resolvedModule : resolvedModules) {
            if(name.equals(getResolvedModuleName(resolvedModule))) {
                if(Objects.isNull(firstMatch)) firstMatch = resolvedModule;
                else otherMatches.add(resolvedModule);
            }
        }
        if(Objects.nonNull(firstMatch)) {
            getResolvedModule(firstMatch).setName(newName);
            resolvedModules.removeAll(otherMatches);
            return true;
        }
        return false;
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
    
    public void printGraph() {
        printGraph(true);
    }
    
    public void printGraph(boolean printParents) {
        printGraph(findLayerName(),printParents);
    }
    
    public void printGraph(String layerName, boolean printParents) {
        logOrPrint("Printing module resolution graph for configuration in "+layerName,Logger::debug);
        for(Entry<Object,Set<Object>> graphEntry : graph(false).entrySet()) {
            logOrPrint("\tMODULE "+getResolvedModuleName(graphEntry.getKey()),Logger::debug);
            for(Object value : graphEntry.getValue())
                logOrPrint("\t\tREADS "+getResolvedModuleName(value),Logger::debug);
        }
        logOrPrint("Finished printing module resolution graph for "+layerName,Logger::debug);
        if(printParents)
            for(Object parent : parents())
                getConfiguration(parent).printGraph();
    }
    
    @IndirectCallers
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
    
    @IndirectCallers
    public void setParents(List<Object> parents) {
        setDirect("parents",Collections.unmodifiableList(parents));
    }
}