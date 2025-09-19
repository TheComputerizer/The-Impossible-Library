package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.bootstrap;

import cpw.mods.modlauncher.Launcher;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Hacks;
import mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILForgeLikeServiceLauncher;
import mods.thecomputerizer.theimpossiblelibrary.api.core.modules.ModuleAccess;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.NeoForgeCoreLoader;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.modules.NeoforgeModuleAccess;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.BOOT;
import static cpw.mods.modlauncher.api.IModuleLayerManager.Layer.SERVICE;
import static java.lang.System.err;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.BOOT_ID;

public class TILLauncherNeoForge extends TILForgeLikeServiceLauncher {
    
    public static void validateBootClass(Logger logger, String moduleName, String className) {
        logger.debug("Validating that {} can be found in BOOT layer module {}",className,moduleName);
        ModuleAccess module = NeoforgeModuleAccess.findModuleInLayer(moduleName,BOOT);
        if(Objects.isNull(module)) {
            logger.error("Cannot validate BOOT loaded class with missing module {} (class = {})",
                         moduleName,className);
            return;
        }
        validateBootClass(logger,Launcher.class.getClassLoader(),module.accessAs(),className);
        logger.debug("Finished validation check for {}",className);
    }
    
    static void validateBootClass(Logger logger, ClassLoader bootLoader, Module module, String className) {
        try {
            Class<?> c = Hacks.callOnOtherClassLoader(bootLoader,"invokeDirect",bootLoader,"findClass",
                    new Object[]{BOOT_ID,className});
            String moduleName = module.getName();
            if(Objects.nonNull(c)) {
                Hacks.callOnOtherClassLoader(bootLoader,"setFieldDirect",c,"module",module);
                logger.info("Added service class {} to BOOT layer module {}",className,moduleName);
            } else logger.error("Failed to find BOOT layer class {} for module {}!",className,moduleName);
        } catch(Throwable t) {
            err.println("Error validating class for BOOT layer "+className);
            t.printStackTrace(err);
        }
    }
    
    public TILLauncherNeoForge(Class<?> caller) {
        super(Launcher.class.getClassLoader(),"Neoforge");
        load(caller);
    }
    
    @Override protected String coreLoader() {
        return "mods.thecomputerizer.theimpossiblelibrary.neoforge.core.NeoForgeCoreLoader";
    }
    
    @Override protected Object initCoreAPI() {
        return NeoForgeCoreLoader.initCoreAPI();
    }
    
    @Override protected String modLoading() {
        return "mods.thecomputerizer.theimpossiblelibrary.neoforge.core.loader.NeoForgeModLoading";
    }
    
    @Override protected String moduleName(Class<?> c) {
        return c.getModule().getName();
    }
    
    @Override protected void moveModule(String moduleName) {
        NeoforgeModuleAccess.moveModule(SERVICE,BOOT,moduleName,true);
        NeoforgeModuleAccess.renameModule(BOOT,moduleName,BOOT_ID);
    }
}