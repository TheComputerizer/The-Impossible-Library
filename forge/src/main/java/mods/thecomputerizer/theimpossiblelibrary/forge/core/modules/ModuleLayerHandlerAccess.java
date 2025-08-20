package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    
    public Map<ModuleLayerAccess,String> getAllNamedModuleLayers(String ... layerNames) {
        Map<ModuleLayerAccess,String> layerToName = new HashMap<>();
        Map<Object,ModuleLayerAccess> layerToWrapper = new HashMap<>();
        Set<Object> layers = new HashSet<>();
        for(String layerName : layerNames) {
            ModuleLayerAccess layer = getModuleLayer(layerName);
            layers.add(layer.access);
            layerToName.put(layer,layerName);
            layerToWrapper.put(layer.access,layer);
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
        LayerInfoAccess layerInfo = getLayerInfo(ModuleSystemAccessor.getLayerEnum(layerName));
        layerInfo.setLayerName(layerName);
        return layerInfo;
    }
    
    public LayerInfoAccess getLayerInfo(Enum<?> layerEnum) {
        return getLayerInfo(completedLayers().get(layerEnum));
    }
    
    @IndirectCallers
    public ModuleClassLoaderAccess getLayerModuleClassLoader(String layerName) {
        return getLayerInfo(layerName).getModuleClassLoader();
    }
    
    public ModuleLayerAccess getModuleLayer(String layerName) {
        return getLayerInfo(layerName).getModuleLayer();
    }
    
    public Map<Object,List<Object>> layerPathMap() {
        return getDirect("layers");
    }
}
