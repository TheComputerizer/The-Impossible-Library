package mods.thecomputerizer.theimpossiblelibrary.forge.core.bootstrap;

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
public class TILBootLauncherForge extends TILLauncher implements ILaunchPluginService {
    
    static {
        out.println("Class init: "+TILBootLauncherForge.class.getName());
    }
    
    @IndirectCallers
    public TILBootLauncherForge() {
        super(true);
    }
    
    @Override public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
        return none(Phase.class);
    }
}