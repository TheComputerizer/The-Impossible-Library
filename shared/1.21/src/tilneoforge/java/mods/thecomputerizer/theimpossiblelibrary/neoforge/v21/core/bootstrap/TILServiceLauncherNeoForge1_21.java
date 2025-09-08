package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.core.bootstrap;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.core.bootstrap.TILLauncherNeoForge;
import net.neoforged.neoforgespi.ILaunchContext;
import net.neoforged.neoforgespi.locating.IDiscoveryPipeline;
import net.neoforged.neoforgespi.locating.IModFileCandidateLocator;

import static java.lang.System.out;

/**
 * Use a dummy IModLocator for Forge SERVICE layer initialization.
 * If this is a dev environment, we already loaded into the BOOT layer and this will do nothing.
 * If this is NOT a dev environment, we need to move ourselves into the BOOT layer.
 * Try not to load too many library classes before verifying we are in the BOOT layer
 */
@IndirectCallers
public class TILServiceLauncherNeoForge1_21 implements IModFileCandidateLocator {
    
    static {
        out.println("Class init: "+TILServiceLauncherNeoForge1_21.class.getName());
        TILLauncherNeoForge.checkInit(TILServiceLauncherNeoForge1_21.class);
    }
    
    @Override public void findCandidates(ILaunchContext context, IDiscoveryPipeline pipeline) {
    }
}