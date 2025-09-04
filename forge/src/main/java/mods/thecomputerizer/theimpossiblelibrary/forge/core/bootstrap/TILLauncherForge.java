package mods.thecomputerizer.theimpossiblelibrary.forge.core.bootstrap;

import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.bootstrap.TILLauncher;
import org.objectweb.asm.Type;

import java.util.EnumSet;

import static java.lang.System.out;

/**
 * ILaunchHandlerService and ILaunchPluginService both load in the BOOT layer with ILaunchPluginService loading after
 * and serving as a decorator for ILaunchHandlerService.
 * We need to be loading in the BOOT layer for coremods to work and ILaunchPluginService is more version-agnostic.
 * Note that both this and the Neoforge plugin will be loaded hence the validation checks being required.
 */
public class TILLauncherForge extends TILLauncher implements ILaunchPluginService {
    
    static {
        out.println("Class init: "+TILLauncherForge.class.getName());
    }
    
    @IndirectCallers
    public TILLauncherForge() {
        super("forge",fmlLoader("minecraftforge"));
    }
    
    @Override public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
        return none(Phase.class);
    }
}