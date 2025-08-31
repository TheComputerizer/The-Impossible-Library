package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules;

import cpw.mods.modlauncher.api.IModuleLayerManager.Layer;
import cpw.mods.modlauncher.api.NamedPath;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.AbstractModuleSystemAccessor;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleLayerAccess;
import org.apache.logging.log4j.Logger;

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

import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

/**
 * cpw.mods.modlauncher.ModuleLayerHandler
 * implements cpw.mods.modlauncher.IModuleLayerManager
 */
public class ModuleLayerHandlerAccess extends AbstractModuleSystemAccessor {
    
    ModuleLayerHandlerAccess(Object layerHandler, Object accessorOrLogger) {
        super(layerHandler,accessorOrLogger);
    }
    
    ModuleLayerAccess asModuleLayer(Object moduleLayer) {
        return moduleLayer instanceof ModuleLayerAccess ? (ModuleLayerAccess)moduleLayer : getModuleLayer(moduleLayer);
    }
    
    public Map<Layer,?> completedLayers() {
        return getDirect("completedLayers");
    }
    
    public Map<String,Collection<String>> getAllModuleNames(Layer ... layers) {
        Map<String,Collection<String>> map = new HashMap<>();
        for(Entry<ModuleLayerAccess,String> layerEntry : getAllNamedModuleLayers(layers).entrySet())
            map.put(layerEntry.getValue(),layerEntry.getKey().moduleNames());
        return map;
    }
    
    public Map<ModuleLayerAccess,String> getAllNamedModuleLayers(Layer ... layers) {
        Map<ModuleLayerAccess,String> layerToName = new HashMap<>();
        Map<ModuleLayer,ModuleLayerAccess> layerToWrapper = new HashMap<>();
        Set<ModuleLayer> moduleLayers = new HashSet<>();
        for(Layer layer : layers) {
            ModuleLayerAccess moduleLayer = getModuleLayer(layer);
            moduleLayers.add(moduleLayer.accessAs());
            layerToName.put(moduleLayer,layer.name());
            layerToWrapper.put(moduleLayer.accessAs(),moduleLayer);
            for(Object parent : moduleLayer.parents()) moduleLayers.add((ModuleLayer)parent);
        }
        int unknownParentCounter = 0;
        for(ModuleLayer layer : moduleLayers) {
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
    public ClassLoader getLayerClassLoader(Layer layer) {
        return getLayerInfo(layer).getClassLoader();
    }
    
    public LayerInfoAccess getLayerInfo(Layer layer) {
        Object layerInfo = completedLayers().get(layer);
        LayerInfoAccess layerInfoAccess = NeoforgeModuleAccess.getLayerInfo(layerInfo,this);
        layerInfoAccess.setLayer(layer);
        return layerInfoAccess;
    }
    
    @IndirectCallers
    public ModuleClassLoaderAccess getLayerModuleClassLoader(Layer layer) {
        return getLayerInfo(layer).getModuleClassLoader();
    }
    
    public Map<String,Collection<String>> getLayerPathNames(Layer ... layers) {
        Map<String,Collection<String>> pathMap = new HashMap<>();
        for(Layer layer : layers)
            pathMap.put(layer.name(),getLayerPaths(layer).stream().map(this::printPathOrJar).collect(Collectors.toSet()));
        if(pathMap.isEmpty())
            logOrPrint("Returning empty path map for layers "+Arrays.toString(layers),Logger::warn);
        return pathMap;
    }
    
    public List<Object> getLayerPaths(Layer layer) {
        Map<Layer,List<Object>> map = layerPathMap();
        if(Objects.isNull(layer)) {
            logOrPrintError("Failed to get null Layer! Cannot return paths");
            return Collections.emptyList();
        }
        if(!map.containsKey(layer)) {
            logOrPrint("Layer path map does not contain paths for "+layer,Logger::warn);
            return Collections.emptyList();
        }
        return map.get(layer);
    }
    
    public ModuleLayerAccess getModuleLayer(Layer layer) {
        return getLayerInfo(layer).getModuleLayer();
    }
    
    public Map<Layer,List<Object>> layerPathMap() {
        return getDirect("layers");
    }
    
    public void printAllModuleNames(Layer ... layers) {
        defaultMapPrinter().accept(getAllModuleNames(layers));
    }
    
    @IndirectCallers
    void printForLayers(Function<Layer[],Map<?,?>> mapGetter, Layer ... layers) {
        defaultMapPrinter().accept(mapGetter.apply(layers));
    }
    
    public void printLayerPaths(Layer ... layers) {
        defaultMapPrinter().accept(getLayerPathNames(layers));
    }
    
    /**
     * 1.18.2-1.20.1
     */
    String printPathOrJar(Object pathOrJar) {
        try {
            NamedPath path = Methods.invoke(pathOrJar, "path");
            return "name = '"+path.name()+"' | paths = '"+Arrays.toString(path.paths())+"'";
        } catch(Throwable t) {
            logOrPrintError("Failed to extract path string from "+pathOrJar,t);
        }
        return "Unknown path object '"+pathOrJar+"'";
    }
}
