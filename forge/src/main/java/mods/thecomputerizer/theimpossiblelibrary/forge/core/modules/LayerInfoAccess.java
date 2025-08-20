package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import lombok.Setter;

import java.util.Objects;

/**
 * cpw.mods.modlauncher.ModuleLayerHandler#LayerInfo (record)
 */
public class LayerInfoAccess extends AbstractModuleSystemAccessor {
    
    @Setter private String layerName;
    
    LayerInfoAccess(Object access, Object accessorOrLogger) {
        super(access,accessorOrLogger);
    }
    
    public ClassLoader getClassLoader() {
        return getDirect("cl");
    }
    
    public ModuleClassLoaderAccess getModuleClassLoader() {
        ModuleClassLoaderAccess moduleClassLoader = getModuleClassLoader(getClassLoader());
        if(Objects.nonNull(this.layerName)) moduleClassLoader.setLayerName(this.layerName);
        return moduleClassLoader;
    }
    
    public ModuleLayerAccess getModuleLayer() {
        ModuleLayerAccess moduleLayer = getModuleLayer(getModuleLayerDirect());
        if(Objects.nonNull(this.layerName)) moduleLayer.setLayerName(this.layerName);
        return moduleLayer;
    }
    
    public Object getModuleLayerDirect() {
        return getDirect("layer");
    }
}
