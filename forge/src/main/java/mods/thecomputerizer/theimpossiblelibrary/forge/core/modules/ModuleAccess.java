package mods.thecomputerizer.theimpossiblelibrary.forge.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import org.apache.logging.log4j.Logger;

import java.util.Set;

/**
 * java.lang.Module
 */
public class ModuleAccess extends AbstractModuleSystemAccessor {
    
    ModuleAccess(Object module, Object accessorOrLogger) {
        super(module,accessorOrLogger);
    }
    
    public void addClassIfMissing(String className, ModuleLayerAccess moduleLayer) {
        String moduleName = getName();
        ClassAccess classAccess = getClassAccess(className,false,moduleLayer.findLoader(moduleName));
        if(!classAccess.inModule(this)) {
            logOrPrint("Adding class "+className+" to module "+moduleName,Logger::debug);
            classAccess.setModule(this);
        }
    }
    
    public void exportPackagesToAll() {
        for(String pkg : getPackages()) exportPackageToAll(pkg);
    }
    
    private void exportPackageToAll(String pkg) {
        invokeStaticDirect(MODULE_CLASS,"addExportsToAll0",this.access,pkg);
        invokeStaticDirect(MODULE_CLASS,"addExportsToAllUnnamed0",this.access,pkg);
    }
    
    @IndirectCallers
    public ClassLoader getClassLoader() {
        return invoke("getClassLoader");
    }
    
    public ModuleDescriptorAccess getDescriptor() {
        return getModuleDescriptor(invoke("getDescriptor"));
    }
    
    public ModuleLayerAccess getLayer() {
        return getModuleLayer(invoke("getLayer"));
    }
    
    public String getName() {
        return invoke("getName");
    }
    
    public Set<String> getPackages() {
        return getDescriptor().packages();
    }
    
    public void inheritFrom(ModuleAccess module) {
        getDescriptor().inheritFrom(module.getDescriptor());
    }
    
    public void moveToLayer(String targetLayerName) {
        moveToLayer(getLayerInfo(targetLayerName));
    }
    
    public void moveToLayer(LayerInfoAccess targetLayerInfo) {
        moveToLayer(targetLayerInfo.getModuleLayer(),targetLayerInfo.getClassLoader());
    }
    
    public void moveToLayer(ModuleLayerAccess targetLayer, ClassLoader targetLoader) {
        setLayer(targetLayer);
        setLoader(targetLoader);
        targetLayer.addModule(this);
    }
    
    public void setLayer(ModuleLayerAccess layer) {
        setLayer(layer.access);
    }
    
    public void setLayer(Object layer) {
        setDirect("layer",layer);
    }
    
    public void setLoader(ClassLoaderAccess loader) {
        setLoader(loader.unwrap());
    }
    
    public void setLoader(ClassLoader loader) {
        setDirect("loader",loader);
    }
    
    public void setName(String name) {
        setDirect("name",name);
        getDescriptor().setName(name);
    }
}