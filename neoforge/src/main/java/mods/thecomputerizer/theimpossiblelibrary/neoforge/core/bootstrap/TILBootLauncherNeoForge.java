package mods.thecomputerizer.theimpossiblelibrary.neoforge.core.bootstrap;

import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncher;
import org.objectweb.asm.Type;

import java.util.EnumSet;

import static java.lang.System.out;

/**
 * In a dev environment, we can add ourselves to the legacy classpath for BOOT layer service discovery
 * We can make use of this by implementing ILaunchPluginService
 */
public class TILBootLauncherNeoForge extends TILLauncher implements ILaunchPluginService {
    
    static {
        out.println("Class init: "+TILBootLauncherNeoForge.class.getName());
    }
    
    @IndirectCallers
    public TILBootLauncherNeoForge() {
        super(true);
    }
    
    @Override public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
        return none(Phase.class);
    }
}