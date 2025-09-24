package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.AbstractModuleSystemAccessor;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleLayerAccess;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader.SECURE_CLASSLOADER_FORMAT;

/**
 * cpw.mods.modlauncher.ModuleLayerHandler
 * implements cpw.mods.modlauncher.IModuleLayerManager
 */
public class ModuleLayerHandlerAccess extends AbstractModuleSystemAccessor {
    
    ModuleLayerHandlerAccess(Object access, Object accessorOrLogger) {
        super(access,accessorOrLogger);
    }
    
    ModuleLayerAccess asModuleLayer(Object moduleLayer) {
        return moduleLayer instanceof ModuleLayerAccess ? (ModuleLayerAccess)moduleLayer : getModuleLayer(moduleLayer);
    }
    
    public Map<Enum<?>,?> completedLayers() {
        return getDirect("completedLayers");
    }
    
    public Map<String,Collection<String>> getAllModuleNames(String ... layerNames) {
        Map<String,Collection<String>> map = new HashMap<>();
        for(Entry<ModuleLayerAccess,String> layerEntry : getAllNamedModuleLayers(layerNames).entrySet())
            map.put(layerEntry.getValue(),layerEntry.getKey().moduleNames());
        return map;
    }
    
    public Map<ModuleLayerAccess,String> getAllNamedModuleLayers(String ... layerNames) {
        Map<ModuleLayerAccess,String> layerToName = new HashMap<>();
        Map<Object,ModuleLayerAccess> layerToWrapper = new HashMap<>();
        Set<Object> layers = new HashSet<>();
        for(String layerName : layerNames) {
            ModuleLayerAccess layer = getModuleLayer(layerName);
            layers.add(layer.access());
            layerToName.put(layer,layerName);
            layerToWrapper.put(layer.access(),layer);
            List<Object> parents = layer.parents();
            layers.addAll(parents);
        }
        int unknownParentCounter = 0;
        for(Object layer : layers) {
            if(!layerToWrapper.containsKey(layer)) {
                ModuleLayerAccess layerAccess = asModuleLayer(layer);
                layerToWrapper.put(layer,layerAccess);
                layerToName.put(layerAccess,"UNKNOWN-PARENT-"+unknownParentCounter);
                unknownParentCounter++;
            }
        }
        return layerToName;
    }
    
    @IndirectCallers
    public ClassLoader getLayerClassLoader(String layerName) {
        return getLayerInfo(layerName).getClassLoader();
    }
    
    public LayerInfoAccess getLayerInfo(String layerName) {
        LayerInfoAccess layerInfo = getLayerInfo(ForgeModuleAccess.getLayerEnum(layerName));
        layerInfo.setLayerName(layerName);
        return layerInfo;
    }
    
    public LayerInfoAccess getLayerInfo(Enum<?> layerEnum) {
        Object layerInfo = completedLayers().get(layerEnum);
        return ForgeModuleAccess.getLayerInfo(layerInfo,this);
    }
    
    @IndirectCallers
    public ModuleClassLoaderAccess getLayerModuleClassLoader(String layerName) {
        return getLayerInfo(layerName).getModuleClassLoader();
    }
    
    public Map<String,Collection<String>> getLayerPathNames(String ... layerNames) {
        Map<String,Collection<String>> pathMap = new HashMap<>();
        Function<Object,String> printer = SECURE_CLASSLOADER_FORMAT ? this::printSecureJar : this::printPathOrJar;
        for(String layerName : layerNames)
            pathMap.put(layerName,getLayerPaths(layerName).stream().map(printer).collect(Collectors.toSet()));
        if(pathMap.isEmpty())
            logOrPrint("Returning empty path map for layers "+Arrays.toString(layerNames),Logger::warn);
        return pathMap;
    }
    
    public List<Object> getLayerPaths(String layerName) {
        Map<Object,List<Object>> map = layerPathMap();
        Enum<?> layerEnum = ForgeModuleAccess.getLayerEnum(layerName);
        if(Objects.isNull(layerEnum)) {
            logOrPrintError("Failed to get Layer "+layerName+"! Cannot return paths");
            return Collections.emptyList();
        }
        if(!map.containsKey(layerEnum)) {
            logOrPrint("Layer path map does not contain paths for "+layerName, Logger::warn);
            return Collections.emptyList();
        }
        return map.get(layerEnum);
    }
    
    public ModuleLayerAccess getModuleLayer(String layerName) {
        return getLayerInfo(layerName).getModuleLayer();
    }
    
    public Map<Object,List<Object>> layerPathMap() {
        return getDirect("layers");
    }
    
    @IndirectCallers
    public void printAllModuleNames(String ... layerNames) {
        defaultMapPrinter().accept(getAllModuleNames(layerNames));
    }
    
    @IndirectCallers
    void printForLayers(Function<String[],Map<?,?>> mapGetter, String ... layerNames) {
        defaultMapPrinter().accept(mapGetter.apply(layerNames));
    }
    
    @IndirectCallers
    public void printLayerPaths(String ... layerNames) {
        defaultMapPrinter().accept(getLayerPathNames(layerNames));
    }
    
    /**
     * 1.18.2-1.20.1
     */
    String printPathOrJar(Object pathOrJar) {
        try {
            Object namedPath = Hacks.invoke(pathOrJar,"path");
            String name = Hacks.invoke(namedPath,"name");
            Path[] paths = Hacks.invoke(namedPath,"paths");
            return "name = '"+name+"' | paths = '"+Arrays.toString(paths)+"'";
        } catch(Throwable t) {
            logOrPrintError("Failed to extract path string from "+pathOrJar,t);
        }
        return "Unknown path object '"+pathOrJar+"'";
    }
    
    /**
     * 1.20.4+
     */
    String printSecureJar(Object secureJar) {
        return ForgeModuleAccess.getSecureJar(secureJar,this).print();
    }
}
