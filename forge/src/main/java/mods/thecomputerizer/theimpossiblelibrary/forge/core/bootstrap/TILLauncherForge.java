package mods.thecomputerizer.theimpossiblelibrary.forge.core.bootstrap;

import cpw.mods.modlauncher.Launcher;
import mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILForgeLikeServiceLauncher;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.modules.ForgeModuleAccess;

import java.util.Objects;

public class TILLauncherForge extends TILForgeLikeServiceLauncher {
    
    public TILLauncherForge(Class<?> caller) {
        super(java8() ? ClassLoader.getSystemClassLoader() : Launcher.class.getClassLoader(),caller,"Forge");
    }
    
    @Override protected String coreLoader() {
        return "mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader";
    }
    
    @Override protected Object initCoreAPI() {
        return ForgeCoreLoader.initCoreAPI();
    }
    
    @Override protected String modLoading() {
        return "mods.thecomputerizer.theimpossiblelibrary.forge.core.loader.ForgeModLoading";
    }
    
    @Override protected String moduleName(Class<?> c) {
        Object module = callMethod(getMethod(Class.class,"getModule"),TILServiceLauncherForge.class);
        if(Objects.isNull(module)) return null;
        Object moduleName = callMethod(getMethod(module.getClass(),"getName"),module);
        return moduleName instanceof String ? (String)moduleName : null;
    }
    
    @Override protected void moveModule(String moduleName) {
        ForgeModuleAccess.moveModule("SERVICE","BOOT",moduleName,true);
    }
}