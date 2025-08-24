package mods.thecomputerizer.theimpossiblelibrary.api.core.modules;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.Objects;

/**
 * java.lang.Class
 */
public class ClassAccess extends AbstractModuleSystemAccessor {
    
    ClassAccess(Class<?> clazz, Object accessorOrLogger) {
        super(clazz,accessorOrLogger);
    }
    
    public ModuleAccess getModule() {
        return getModule(getModuleDirect());
    }
    
    public Object getModuleDirect() {
        return invoke("getModule");
    }
    
    public String getModuleName() {
        return getModule().getName();
    }
    
    public boolean inModule(String ... moduleNames) {
        ModuleAccess module = getModule();
        if(Objects.isNull(module)) return false;
        String name = module.getName();
        for(String moduleName : moduleNames)
            if(moduleName.equals(name)) return true;
        return false;
    }
    
    @IndirectCallers
    public boolean inModule(ModuleAccess module) {
        return inModule(module.access);
    }
    
    public boolean inModule(Object module) {
        return getModuleDirect()==module;
    }
    
    /**
     * Returns true if the class is moved
     */
    @IndirectCallers
    public boolean moveIfInModule(ClassLoaderAccess loader, ModuleAccess module, String ... moduleNames) {
        if(!inModule(moduleNames)) return false;
        moveTo(loader,module);
        return true;
    }
    
    public void moveTo(ClassLoaderAccess loader, ModuleAccess module) {
        setClassLoader(loader);
        setModule(module);
    }
    
    public void setClassLoader(ClassLoaderAccess loader) {
        setClassLoader(loader.unwrap());
    }
    
    public void setClassLoader(ClassLoader loader) {
        setDirect("classLoader",loader);
    }
    
    public void setModuleIfIn(ModuleAccess module, String ... moduleNames) {
        if(inModule(moduleNames)) setModule(module);
    }
    
    public void setModule(ModuleLayerAccess moduleLayerAccess, String moduleName) {
        setModule(moduleLayerAccess.getModule(moduleName));
    }
    
    public void setModule(ModuleAccess module) {
        setModule(module.access);
    }
    
    public void setModule(Object module) {
        setDirect("module",module);
    }
    
    public <T> Class<T> unwrap() {
        return as(this.access);
    }
}