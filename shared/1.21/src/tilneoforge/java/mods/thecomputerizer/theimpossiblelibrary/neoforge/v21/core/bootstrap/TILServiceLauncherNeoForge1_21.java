package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core.bootstrap;

import cpw.mods.modlauncher.Launcher;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILForgeLikeServiceLauncher;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.bootstrap.TILLauncherNeoForge;
import net.neoforged.neoforgespi.ILaunchContext;
import net.neoforged.neoforgespi.locating.IDiscoveryPipeline;
import net.neoforged.neoforgespi.locating.IModFileCandidateLocator;
import org.apache.logging.log4j.Logger;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncherRef.launcher;

/**
 * Use a dummy IModLocator for Forge SERVICE layer initialization.
 * If this is a dev environment, we already loaded into the BOOT layer and this will do nothing.
 * If this is NOT a dev environment, we need to move ourselves into the BOOT layer.
 * Try not to load too many library classes before verifying we are in the BOOT layer
 */
@IndirectCallers
public class TILServiceLauncherNeoForge1_21 implements IModFileCandidateLocator {
    
    static final String LANGUAGE_LOADER = "mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core.MultiVersionLanguageLoader";
    static final String LOCATOR = "mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core.TILSelfLocator";
    static final String READER = "mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core.MultiVersionModReader";
    
    static {
        Class<?> c = TILServiceLauncherNeoForge1_21.class;
        if(c.getClassLoader()!=Launcher.class.getClassLoader()) {
            TILForgeLikeServiceLauncher.init(c,TILLauncherNeoForge.class);
            validateServices(c.getName(),LOCATOR,READER,LANGUAGE_LOADER);
        }
    }
    
    static void validateServices(String ... classNames) {
        Logger logger = launcher.getLogger();
        for(String className : classNames) TILLauncherNeoForge.validateBootClass(logger,MODID,className);
    }
    
    @Override public void findCandidates(ILaunchContext context, IDiscoveryPipeline pipeline) {}
}