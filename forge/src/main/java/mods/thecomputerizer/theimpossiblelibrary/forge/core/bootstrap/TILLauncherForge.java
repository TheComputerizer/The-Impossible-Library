package mods.thecomputerizer.theimpossiblelibrary.forge.core.bootstrap;

import cpw.mods.modlauncher.Launcher;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILForgeLikeServiceLauncher;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.ForgeCoreLoader;
import mods.thecomputerizer.theimpossiblelibrary.forge.core.modules.ForgeModuleAccess;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

import static java.lang.System.err;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.BOOT_ID;

public class TILLauncherForge extends TILForgeLikeServiceLauncher {
    
    static ClassLoader setBootLoader() {
        return java8() ? ClassLoader.getSystemClassLoader() : Launcher.class.getClassLoader();
    }
    
    public static void validateBootClass(Logger logger, String moduleName, String className) {
        logger.debug("Validating that {} can be found in BOOT layer module {}",className,moduleName);
        ModuleAccess module = ForgeModuleAccess.findModuleInLayer(moduleName,"BOOT");
        if(Objects.isNull(module)) {
            logger.error("Cannot validate BOOT loaded class with missing module {} (class = {})",
                         moduleName,className);
            return;
        }
        validateBootClass(logger,Launcher.class.getClassLoader(),module,className);
        logger.debug("Finished validation check for {}",className);
    }
    
    static void validateBootClass(Logger logger, ClassLoader bootLoader, ModuleAccess module, String className) {
        try {
            Class<?> c = Hacks.callOnOtherClassLoader(bootLoader,"invokeDirect",bootLoader,"findClass",
                                                      new Object[]{BOOT_ID,className});
            String moduleName = module.getName();
            if(Objects.nonNull(c)) {
                Hacks.callOnOtherClassLoader(bootLoader,"setFieldDirect",c,"module",module.access());
                logger.info("Added service class {} to BOOT layer module {}",className,moduleName);
            } else logger.error("Failed to find BOOT layer class {} for module {}!",className,moduleName);
        } catch(Throwable t) {
            err.println("Error validating class for BOOT layer "+className);
            t.printStackTrace(err);
        }
    }
    
    public TILLauncherForge(Class<?> caller) {
        super(setBootLoader(),"Forge");
        load(caller);
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
        this.logger.info("Moving module {} from SERVICE to BOOT layer",moduleName);
        ForgeModuleAccess.moveModule("SERVICE","BOOT",moduleName,true);
        this.logger.info("Renaming BOOT module from {} to {}",moduleName,BOOT_ID);
        ForgeModuleAccess.renameModule("BOOT",moduleName,BOOT_ID);
    }
}