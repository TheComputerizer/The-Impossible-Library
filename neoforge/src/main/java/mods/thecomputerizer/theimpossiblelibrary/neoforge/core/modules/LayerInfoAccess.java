package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules;

import cpw.mods.modlauncher.api.IModuleLayerManager.Layer;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.AbstractModuleSystemAccessor;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleLayerAccess;

import java.util.Objects;

/**
 * cpw.mods.modlauncher.ModuleLayerHandler#LayerInfo (record)
 */
public class LayerInfoAccess extends AbstractModuleSystemAccessor {
    
    @Setter private Layer layer;
    
    LayerInfoAccess(Object layerInfo, Object accessorOrLogger) {
        super(layerInfo,accessorOrLogger);
    }
    
    public ClassLoader getClassLoader() {
        return invokeDirect("cl");
    }
    
    public ModuleClassLoaderAccess getModuleClassLoader() {
        ModuleClassLoaderAccess moduleClassLoader = NeoforgeModuleAccess.getModuleClassLoader(getClassLoader());
        if(Objects.nonNull(this.layer)) moduleClassLoader.setLayer(this.layer);
        return moduleClassLoader;
    }
    
    public ModuleLayerAccess getModuleLayer() {
        ModuleLayerAccess moduleLayer = getModuleLayer(getModuleLayerDirect());
        if(Objects.nonNull(this.layer)) moduleLayer.setLayerName(this.layer.name());
        return moduleLayer;
    }
    
    public ModuleLayer getModuleLayerDirect() {
        return getDirect("layer");
    }
}